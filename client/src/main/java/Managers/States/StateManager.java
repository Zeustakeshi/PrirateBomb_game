package Managers.States;


import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


// T : Constants type
public class StateManager<T, State> {

    public static abstract class BaseState<T> {
        public T state;
        public BaseState (T state) {
            this.state = state;
        }

        public abstract void enter ();
        public abstract void update();
        public abstract boolean onAnimationEnd();

    }

    private final Map<T, State> states;

    private final Stack<State> stackState;

    public StateManager () {
        this.states = new HashMap<>();
        this.stackState = new Stack<>();
    }

    public State findState (T key) {
        return this.states.get(key);
    }

    public State getCurrentState () {
        if (!this.stackState.isEmpty()) {
            return this.stackState.peek();
        }else {
            return null;
        }
    }



    public void setState (T state, boolean savePreviousState) {
        if (!savePreviousState && !this.stackState.isEmpty()) this.stackState.pop();
        this.stackState.push(this.findState(state));
    }

    public void setState (T state) {
        if (!this.stackState.isEmpty()) this.stackState.pop();
        this.stackState.push(this.findState(state));


    }


    public void addState (T key, State state) {
        this.states.put(key, state);
    }

    public void removeState () {
        if (this.stackState.size() > 1)  {
            this.stackState.pop();
        }
    }

}
