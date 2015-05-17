package org.androplus.phone;


import android.content.res.XModuleResources;
import android.view.View;

import java.util.Locale;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;
import de.robv.android.xposed.callbacks.XC_LayoutInflated;
import de.robv.android.xposed.XposedBridge;

public class NotificationiconPatcher implements IXposedHookZygoteInit, IXposedHookInitPackageResources {
	private static XSharedPreferences preference = null;
	private static String MODULE_PATH = null;

	@Override
	public void initZygote(StartupParam startupParam) throws Throwable {
	preference = new XSharedPreferences(NotificationiconPatcher.class.getPackage().getName());
		MODULE_PATH = startupParam.modulePath;
	}

	@Override
	public void handleInitPackageResources(InitPackageResourcesParam resparam) throws Throwable {
		String s = preference.getString("key_prefedit", "0,1,2,3,4,5,6,7,8,9,10,11");
		
		if (!(resparam.packageName.equals("com.android.phone")||resparam.packageName.equals("com.android.incallui")||resparam.packageName.equals("com.android.server.telecom")||resparam.packageName.equals("com.sonyericsson.android.socialphonebook")||resparam.packageName.equals("android")||resparam.packageName.equals("com.sonyericsson.soundenhancement")))
			return;

		if (resparam.packageName.equals("com.android.phone")){
		XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res);
		
		// Disable data charge warning
		boolean isDataw = preference.getBoolean("key_dataw", false);

		if(isDataw){
			try {
			resparam.res.setReplacement("com.android.phone", "bool", "disable_charge_popups", true);
			} catch (Throwable t) {
			XposedBridge.log(t.getMessage());
			}
		}

		// Hide icon on data state changed
		boolean isDataicon = preference.getBoolean("key_dataicon", false);

		if(isDataicon){
			try {
			resparam.res.setReplacement("com.android.phone", "bool", "data_connection_except_mms_show_icon_when_disabled", false);
			resparam.res.setReplacement("com.android.phone", "bool", "data_connection_except_mms_show_icon_when_enabled", false);
			} catch (Throwable t) {
			XposedBridge.log(t.getMessage());
			}
		}

		// Show Use only 3G networks
		boolean is3gusef = preference.getBoolean("key_3guse", false);

		if(is3gusef){
			try {
			resparam.res.setReplacement("com.android.phone", "bool", "use_3g_only", true);
			} catch (Throwable t) {
			XposedBridge.log(t.getMessage());
			}
		}
		
		// Change network mode
		boolean isPrefmodee = preference.getBoolean("key_prefmode", false);
		if(isPrefmodee){
			try {
			resparam.res.setReplacement("com.android.phone", "string", "preferred_network_mode_marshal", s);
			} catch (Throwable t) {
			XposedBridge.log(t.getMessage());
			}
		}

		// Show APN settings with CDMA
		boolean isCDMAAPN = preference.getBoolean("key_apn_cdma", false);

		if(isCDMAAPN){
			try {
			resparam.res.setReplacement("com.android.phone", "bool", "config_show_apn_setting_cdma", true);
			resparam.res.setReplacement("com.android.phone", "bool", "config_show_cdma", true);
			} catch (Throwable t) {
			XposedBridge.log(t.getMessage());
			}

		}
		
		// Enable call blocking
		boolean isCallblock = preference.getBoolean("key_call_block", false);

		if(isCallblock && Locale.JAPAN.equals(Locale.getDefault())){
				try {
				resparam.res.setReplacement("com.android.phone", "string", "phone_settings_call_blocking_txt", modRes.fwd(R.string.phone_settings_call_blocking_txt));
			} catch (Throwable t) {
			XposedBridge.log(t.getMessage());
			}
		}
		
		// Enable prefix
		boolean isPrefix = preference.getBoolean("key_add_prefix", false);

		if(isPrefix){
			try {
			resparam.res.setReplacement("com.android.phone", "bool", "config_enable_prefix_settings", true);
			} catch (Throwable t) {
			XposedBridge.log(t.getMessage());
			}
			if (Locale.JAPAN.equals(Locale.getDefault())){
				try {
				resparam.res.setReplacement("com.android.phone", "string", "phone_settings_prefix_setting_txt", modRes.fwd(R.string.phone_settings_prefix_setting_txt));
				resparam.res.setReplacement("com.android.phone", "string", "phone_settings_add_prefix_number_txt", modRes.fwd(R.string.phone_settings_add_prefix_number_txt));
				resparam.res.setReplacement("com.android.phone", "string", "phone_settings_add_prefix_title_txt", modRes.fwd(R.string.phone_settings_add_prefix_title_txt));
				resparam.res.setReplacement("com.android.phone", "string", "phone_settings_code_limitation_toast_txt", modRes.fwd(R.string.phone_settings_code_limitation_toast_txt));
				resparam.res.setReplacement("com.android.phone", "string", "phone_settings_context_menu_delete_txt", modRes.fwd(R.string.phone_settings_context_menu_delete_txt));
				resparam.res.setReplacement("com.android.phone", "string", "phone_settings_context_menu_edit_txt", modRes.fwd(R.string.phone_settings_context_menu_edit_txt));
				resparam.res.setReplacement("com.android.phone", "string", "phone_settings_edit_item_code_txt", modRes.fwd(R.string.phone_settings_edit_item_code_txt));
				resparam.res.setReplacement("com.android.phone", "string", "phone_settings_edit_item_name_txt", modRes.fwd(R.string.phone_settings_edit_item_name_txt));
				resparam.res.setReplacement("com.android.phone", "string", "phone_settings_edit_prefix_title_txt", modRes.fwd(R.string.phone_settings_edit_prefix_title_txt));
				resparam.res.setReplacement("com.android.phone", "string", "phone_settings_prefix_delete_dialog_txt", modRes.fwd(R.string.phone_settings_prefix_delete_dialog_txt));
				resparam.res.setReplacement("com.android.phone", "string", "phone_settings_prefix_max_dialog_text_txt", modRes.fwd(R.string.phone_settings_prefix_max_dialog_text_txt));
				resparam.res.setReplacement("com.android.phone", "string", "phone_settings_item_added_toast_txt", modRes.fwd(R.string.phone_settings_item_added_toast_txt));
				resparam.res.setReplacement("com.android.phone", "string", "phone_settings_item_changed_toast_txt", modRes.fwd(R.string.phone_settings_item_changed_toast_txt));
				resparam.res.setReplacement("com.android.phone", "string", "phone_settings_item_deleted_toast_txt", modRes.fwd(R.string.phone_settings_item_deleted_toast_txt));
				resparam.res.setReplacement("com.android.phone", "string", "phone_settings_name_limitation_toast_txt", modRes.fwd(R.string.phone_settings_name_limitation_toast_txt));
			} catch (Throwable t) {
			XposedBridge.log(t.getMessage());
			}
			}
		}
		
		}//End
		
		
		// Z3 InCallUI.apk
		if (resparam.packageName.equals("com.android.incallui")){
		XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res);
		
		// Disable VoLTE icon
		boolean isHidevolteicon = preference.getBoolean("key_hide_volte_icon", false);

		if(isHidevolteicon){
			try {
			resparam.res.setReplacement("com.android.incallui", "bool", "enable_volte_icon", false);
			} catch (Throwable t) {
			XposedBridge.log(t.getMessage());
			}

		}
		
		// Hide reject call with message
		boolean isRejectmsgz3 = preference.getBoolean("key_rejectmsgz3", false);

		if(isRejectmsgz3){
			try {
			resparam.res.setReplacement("com.android.incallui", "dimen", "reject_msgs_drawer_height", modRes.fwd(R.dimen.reject_msgs_drawer_height));
			resparam.res.setReplacement("com.android.incallui", "dimen", "call_large_header_height", modRes.fwd(R.dimen.call_large_header_height));
			resparam.res.setReplacement("com.android.incallui", "dimen", "reject_msgs_list_padding_top", modRes.fwd(R.dimen.reject_msgs_list_padding_top));
			/*resparam.res.hookLayout("com.android.incallui", "layout", "somc_reject_with_msgs_list_item", new XC_LayoutInflated() {
			    @Override
			    public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
			    	liparam.view.findViewById(liparam.res.getIdentifier("text", "id", "com.android.incallui")).setVisibility(View.GONE);
			    }
			    }); */
			    } catch (Throwable t) {
			XposedBridge.log(t.getMessage());
			}
		}

		}//End
		
		// Telecom
		if (resparam.packageName.equals("com.android.server.telecom")){
		XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res);
		
		// Enable call blocking
		boolean isCallblock = preference.getBoolean("key_call_block", false);

		if(isCallblock){
			try {
			resparam.res.setReplacement("com.android.server.telecom", "bool", "config_enable_call_blocking", true);
			} catch (Throwable t) {
			XposedBridge.log(t.getMessage());
			}
			if (Locale.JAPAN.equals(Locale.getDefault())){
				try {
				resparam.res.setReplacement("com.android.server.telecom", "string", "phone_settings_call_blocking_txt", modRes.fwd(R.string.phone_settings_call_blocking_txt));
				resparam.res.setReplacement("com.android.server.telecom", "string", "phone_settings_block_calls_setting_txt", modRes.fwd(R.string.phone_settings_block_calls_setting_txt));
				resparam.res.setReplacement("com.android.server.telecom", "string", "phone_settings_block_calls_setting_summary_txt", modRes.fwd(R.string.phone_settings_block_calls_setting_summary_txt));
				resparam.res.setReplacement("com.android.server.telecom", "string", "phone_settings_block_types_of_calls_txt", modRes.fwd(R.string.phone_settings_block_types_of_calls_txt));
				resparam.res.setReplacement("com.android.server.telecom", "string", "phone_settings_number_not_in_contact_txt", modRes.fwd(R.string.phone_settings_number_not_in_contact_txt));
				resparam.res.setReplacement("com.android.server.telecom", "string", "phone_settings_number_not_in_contact_summary_txt", modRes.fwd(R.string.phone_settings_number_not_in_contact_summary_txt));
				resparam.res.setReplacement("com.android.server.telecom", "string", "phone_settings_private_num_txt", modRes.fwd(R.string.phone_settings_private_num_txt));
				resparam.res.setReplacement("com.android.server.telecom", "string", "phone_settings_private_num_summary_txt", modRes.fwd(R.string.phone_settings_private_num_summary_txt));
				resparam.res.setReplacement("com.android.server.telecom", "string", "phone_settings_payphone_txt", modRes.fwd(R.string.phone_settings_payphone_txt));
				resparam.res.setReplacement("com.android.server.telecom", "string", "phone_settings_payphone_summary_txt", modRes.fwd(R.string.phone_settings_payphone_summary_txt));
				resparam.res.setReplacement("com.android.server.telecom", "string", "phone_settings_unknown_txt", modRes.fwd(R.string.phone_settings_unknown_txt));
				resparam.res.setReplacement("com.android.server.telecom", "string", "phone_settings_unknown_summary_txt", modRes.fwd(R.string.phone_settings_unknown_summary_txt));
				resparam.res.setReplacement("com.android.server.telecom", "string", "phone_strings_note_dialog_title_txt", modRes.fwd(R.string.phone_strings_note_dialog_title_txt));
				resparam.res.setReplacement("com.android.server.telecom", "string", "phone_settings_call_blocking_turned_off_emergency_dialog_text_txt", modRes.fwd(R.string.phone_settings_call_blocking_turned_off_emergency_dialog_text_txt));
				resparam.res.setReplacement("com.android.server.telecom", "string", "phone_strings_call_blocking_turned_off_notification_title_txt", modRes.fwd(R.string.phone_strings_call_blocking_turned_off_notification_title_txt));
				resparam.res.setReplacement("com.android.server.telecom", "string", "phone_strings_call_blocking_turned_off_notification_text_txt", modRes.fwd(R.string.phone_strings_call_blocking_turned_off_notification_text_txt));
				resparam.res.setReplacement("com.android.server.telecom", "string", "phone_strings_emergency_call_made_dialog_title_txt", modRes.fwd(R.string.phone_strings_emergency_call_made_dialog_title_txt));
				resparam.res.setReplacement("com.android.server.telecom", "string", "phone_strings_emergency_call_made_dialog_call_blocking_text_txt", modRes.fwd(R.string.phone_strings_emergency_call_made_dialog_call_blocking_text_txt));
			} catch (Throwable t) {
			XposedBridge.log(t.getMessage());
			}
			}
		}
		// Enable presets
		/*boolean isAnspreset = preference.getBoolean("key_answering_preset", false);

		if(isAnspreset){
			try {
			resparam.res.setReplacement("com.android.server.telecom", "array", "preset_greeting_filename_list", modRes.fwd(R.array.preset_greeting_filename_list));
			resparam.res.setReplacement("com.android.server.telecom", "array", "preset_greeting_displayname_list", modRes.fwd(R.array.preset_greeting_displayname_list));
			} catch (Throwable t) {
			XposedBridge.log(t.getMessage());
			}

		}
		*/

		}//End
		
		// Z3 SoundEnhancement.apk
		if (resparam.packageName.equals("com.sonyericsson.soundenhancement")){
		XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res);
		
		// Disable call ended screen
		boolean isSoundeh = preference.getBoolean("key_soundeh", false);

		if(isSoundeh){
			try {
			resparam.res.setReplacement("com.sonyericsson.soundenhancement", "bool", "enabled_xloud", true);
			resparam.res.setReplacement("com.sonyericsson.soundenhancement", "bool", "enabled_clear_stereo", true);
			resparam.res.setReplacement("com.sonyericsson.soundenhancement", "bool", "enabled_clear_phase", true);
			} catch (Throwable t) {
			XposedBridge.log(t.getMessage());
			}

		}
		

		}//End
		
		// Phonebook
		if (resparam.packageName.equals("com.sonyericsson.android.socialphonebook")){
		XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res);
		
		// Enable speed dial
		boolean isSpeedd = preference.getBoolean("key_speed_dial", false);

		if(isSpeedd){
			try {
			resparam.res.setReplacement("com.sonyericsson.android.socialphonebook", "bool", "enable_speed_dial", true);
			} catch (Throwable t) {
			XposedBridge.log(t.getMessage());
			}
		}
		
		// Enable prefix
		boolean isPrefix = preference.getBoolean("key_add_prefix", false);

		if(isPrefix){
			try {
			resparam.res.setReplacement("com.sonyericsson.android.socialphonebook", "bool", "enable_add_prefix", true);
			} catch (Throwable t) {
			XposedBridge.log(t.getMessage());
			}
		}

		}//End
		
		// framework-res
		/*if (resparam.packageName.equals("android")){
		XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res);
		
		// Force Enable VoLTE
		boolean isFvolte = preference.getBoolean("key_fvolte", false);

		if(isFvolte){
			try {
			resparam.res.setReplacement("android", "bool", "config_carrier_volte_vt_available", true);
			resparam.res.setReplacement("android", "bool", "config_enable_volte_default_value", true);
			} catch (Throwable t) {
			XposedBridge.log(t.getMessage());
			}

		}

		}//End
		*/
		


	}

}
