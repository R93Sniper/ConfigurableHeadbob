package r93.configurableheadbob.client;

import r93.configurableheadbob.client.menu.MenuValues;
import net.fabricmc.api.ClientModInitializer;

public class ConfigurableHeadbobClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MenuValues.load();
	}
}