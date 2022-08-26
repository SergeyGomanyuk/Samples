package events;

import java.util.function.Function;

// per evant type
// catalog events - full, incremental, уес
// mbd - per each event type
class EventsRepository {
    public Events updateEventsAtomically(Function<Events, Events> mapper) {
        return mapper.apply(new Events());
    }
}
