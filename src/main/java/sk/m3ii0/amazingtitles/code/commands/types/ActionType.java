package sk.m3ii0.amazingtitles.code.commands.types;

import java.util.ArrayList;
import java.util.List;

public enum ActionType {
	
	TITLE(),
	SUBTITLE(),
	ACTION_BAR(),
	BOSS_BAR();
	
	ActionType() {}
	
	public static List<String> toIterable() {
		List<String> names = new ArrayList<>();
		for (ActionType type : values()) {
			names.add(type.name());
		}
		return names;
	}
	
}
