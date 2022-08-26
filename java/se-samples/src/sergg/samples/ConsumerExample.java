package sergg.samples;

import lombok.Data;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import static java.util.Comparator.nullsLast;

public class ConsumerExample {
    public static void main(String[] args) {
        new ConsumerExample().doMain();
    }

    public void doMain() {
        Set<PromotionModel> s = new TreeSet<>(new PromotionModelComparator());
        s.add(new PromotionModel(1, "b"));
        s.add(new PromotionModel(2, "b"));
        s.add(new PromotionModel(1, "a"));
        System.out.println(s);
    }


    public class PromotionModelComparator implements Comparator<PromotionModel>  {

        private final Comparator<PromotionModel> comparator;

        public PromotionModelComparator() {
            comparator = Comparator
                    .comparing(PromotionModel::getPriority, nullsLast(Integer::compareTo))
                    .thenComparing(PromotionModel::getId, nullsLast(String::compareTo));
        }

        @Override
        public int compare(PromotionModel o1, PromotionModel o2) {
            return comparator.compare(o1, o2);
        }

    }

    @Data
    private class PromotionModel {
        private final Integer priority;
        private final String id;
    }
}
