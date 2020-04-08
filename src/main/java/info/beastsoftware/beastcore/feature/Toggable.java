package info.beastsoftware.beastcore.feature;

public interface Toggable extends Checkable{
    default void toggle(){
        boolean on = isOn();
        this.getConfig()
                .getConfig()
                .set(getFeatureType().getPath(),!on);
    }
}
