package sk.m3ii0.amazingtitles.code.providers.R1_16_R3;

import sk.m3ii0.amazingtitles.code.spi.NmsBuilder;
import sk.m3ii0.amazingtitles.code.spi.NmsProvider;

public class R1_16_R3_Builder implements NmsBuilder {
	
	
	@Override
	public boolean checked(String version) {
		return version.equals("v1_16_R3");
	}
	
	@Override
	public NmsProvider build() {
		return new R1_16_R3();
	}
	
}
