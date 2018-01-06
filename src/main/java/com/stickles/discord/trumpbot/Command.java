package com.stickles.discord.trumpbot;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
	String Name();

	String[] Aliases() default {};

	String Summary() default "This command does not have a summary yet :/";

	String Syntax() default "";

	String SpecialPerms() default "";
}
