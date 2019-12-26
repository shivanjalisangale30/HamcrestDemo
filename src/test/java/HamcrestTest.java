import Todo.Todo;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class HamcrestTest {

    @Test
    public void coreMatcherTest() {
        Assert.assertThat(Long.valueOf(1), CoreMatchers.instanceOf(Long.class));
        Assert.assertThat(Long.valueOf(1), CoreMatchers.isA(Long.class));
    }

    @Test
    public void listMatcherTest() {
        List<Integer> list = Arrays.asList(5,2,4);
        MatcherAssert.assertThat(list, Matchers.hasSize(3));
        MatcherAssert.assertThat(list,Matchers.contains(5,2,4));
        MatcherAssert.assertThat(list,Matchers.containsInAnyOrder(2,4,5));
        MatcherAssert.assertThat(list,Matchers.everyItem(Matchers.greaterThan(1)));
    }

    @Test
    public void arrayMatcherTest(){
        Integer[] ints = new Integer[] {7,5,12,16};
        MatcherAssert.assertThat(ints,Matchers.arrayWithSize(4));
        MatcherAssert.assertThat(ints,Matchers.arrayContaining(7,5,12,16));
    }

    @Test
    public void objectMatchersTest() {
        Todo todo1 = new Todo("1","Learn Hamcrest","Important");
        Todo todo2 = new Todo("1","Learn Hamcrest","Important");
        MatcherAssert.assertThat(todo1,Matchers.hasProperty("summary"));
        MatcherAssert.assertThat(todo1,Matchers.hasProperty("summary",
                Matchers.equalTo("Learn Hamcrest")));
        MatcherAssert.assertThat(todo1,Matchers.samePropertyValuesAs(todo2));


    }
}
