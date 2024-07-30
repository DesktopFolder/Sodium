package me.jellysquid.mods.sodium.client.gui.vanilla.builders;

import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import net.minecraft.text.Text;
import org.apache.commons.lang3.Validate;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class OptionBuilder<P, T, U> {
    private String key;
    private String text;
    private BiFunction<U, T, Text> textGetter;
    private Function<SodiumGameOptions, U> getter;
    private BiConsumer<SodiumGameOptions, U> setter;

    abstract P self();

    public P setKey(String key){
        this.key = key;
        return self();
    }

    public P setText(String text){
        this.text = text;
        return  self();
    }

    public P setGetter(Function<SodiumGameOptions, U> getter){
        this.getter = getter;
        return self();
    }

    public P setSetter(BiConsumer<SodiumGameOptions, U> setter){
        this.setter = setter;
        return self();
    }

    public P setTextGetter(Function<U, Text> textGetter){
        this.textGetter = (gameOptions, self) -> textGetter.apply(gameOptions);
        return self();
    }

    public P setTextGetter(BiFunction<U, T, Text> textGetter){
        this.textGetter = textGetter;
        return self();
    }

    String getKey(){
        Validate.notNull(key, "Key must be specified");
        return key;
    }

    String getText(){
        return text;
    }

    Function<SodiumGameOptions, U> getGetter(){
        Validate.notNull(getter, "Getter must be specified");
        return getter;
    }

    BiConsumer<SodiumGameOptions, U> getSetter() {
        Validate.notNull(setter, "Setter must be specified");
        return setter;
    }

    BiFunction<SodiumGameOptions, T, Text> getTextGetter(){
        Validate.notNull(textGetter, "Text Getter must be specified");
        Function<SodiumGameOptions, U> getter = getGetter();
        return (gameOptions, option) -> {
            U value = getter.apply(gameOptions);
            return textGetter.apply(value, option);
        };
    }

    public abstract T build();
}
