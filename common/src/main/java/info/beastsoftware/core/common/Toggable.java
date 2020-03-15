package info.beastsoftware.core.common;

public interface Toggable {

    void enable();

    void disable();

    boolean isEnabled();

    default void toggle(){
        if(this.isEnabled()){
            this.disable();
        }
        else{
            this.enable();
        }
    }

}
