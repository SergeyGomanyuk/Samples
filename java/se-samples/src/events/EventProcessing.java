package events;

public class EventProcessing {
    private EventsRepository eventsRepository = new EventsRepository();

    // возможные ситуации:
    // - новый эвент,
    // - эвент в обработке (ретрай после падения или дубликат),
    // - эвент в неуспешных (дубликат или упали в районе посылки failure)
    // - эвент в успешных (дубликат или упали после завершения работ, возможно уже послали success)
    //
    // исключения не ловим -> приходит retry из кафки
    // если ретраи из кафки не помогли (нет доступа к БД или ошибки посылки в кафку),
    // то надеемся на DLQ, куда сообщение попадет после N ретраев
    // слушаем DLQ, если DLQ не пусто -> publishFailure -> последняя миля (или доставили или нет), можно и не делать, например для каталога, потому что у каталога
    void processEvent(String id) {
        // обновляем статус эвента перед обработкой
        // Это понятно, т.е. кас в общем случае должен быть устроен такзагрузили объект по уникальному ключу,
        // если объект null, то создали, проапдейтили, сохранили через INSERT -> если обломались, то обломался наш CAS,
        // начинай сначала если объект не null, то проапдейтили и сохранили через UPSERT c проверкой уникального номера записи, если обломались, то опять начинай сначала

        Events updatedEvents = eventsRepository.updateEventsAtomically(events -> { // каталог - в случае если была одновременная запись - исключение, msbd - cas
            if(events.isNew(id) && canProcess(events, id)) {
                events.moveToProcessing(id);
            } else if(!events.isCompleted(id)) {
                events.moveToFailed(id);
            }
            return events;
        });
        if(updatedEvents.isProcessing(id)) {
            // перевели эвент в состояние обработки и делаем основную работу
            // ----------- если мы хотим try/catch, то в catch помимо publishFailure должен быть updateEventsAtomically с
            // if(!events.isCompleted(id)) {
            //   events.moveToFailed(id);
            // }
            doMainJob();
            // -----------

            // обновляем статус эвента после обработки
            updatedEvents = eventsRepository.updateEventsAtomically(events -> {
                if(events.isProcessing(id)) {
                    events.moveToCompleted(id);
                }
                return events;
            });

            // если удалось перевести в состояние удачной обработки сообщаем, если эвент неожиданно оказался
            // не в processing, то ничего не делаем, пусть делает тот, кто перевел
            if(updatedEvents.isCompleted(id)) publishSuccess(id);
        } else if(updatedEvents.isFailed(id)) {
            // ретрай (упали до окончания работы или другие условия на падения эвента)
            publishFailure(id);
        } else if(updatedEvents.isCompleted(id)) {
            // ретрай (упали после окончания работы)
            publishSuccess(id);
        }
    }
    
    // cancel & commit - если упали, делаем ретраи до последнего
    // если ретраи не помогли, то сообщение помещается в DLQ
    void processCancelEvent(String id) {
        Events updatedEvents = eventsRepository.updateEventsAtomically(events -> {
            if(events.isFailed(id) || events.isCompleted(id) || events.isProcessing(id) ) { // ? || events.isProcessing(id) - timeout?
                doCancel(id); // удаление promo - допустимо и правильно выполнять здесь
                events.moveToCancelled(id);
            }
            return events;
        });
    }

    void processCommitEvent(String id) {
        Events updatedEvents = eventsRepository.updateEventsAtomically(events -> {
            if(events.isCompleted(id)) {
                doCommit(id); // в случае с коммитом пустая операция
                events.moveToComitted(id);
            }
            return events;
        });
    }

    private void doMainJob() { }

    private void doCommit(String id) { }

    private void doCancel(String id) { }

    private boolean canProcess(Events events, String id) {
        return true;
    }

    private void publishFailure(String id) {  }

    private void publishSuccess(String id) {  }
}