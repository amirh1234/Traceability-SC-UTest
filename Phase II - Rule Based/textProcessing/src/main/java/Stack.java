/**
 * Created by Alireza on 6/23/2016.
 */
import java.util.ArrayList;

public class Stack<E> extends ArrayList<E> {

    private static final long serialVersionUID = 1L;

    public E pop() {
        if(size()==0){
            return null;
        }
        E e = get(size() - 1);
        remove(size() - 1);
        return e;
    }

    public void push(E e) {
        add(e);
    }

}