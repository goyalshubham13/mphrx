package com.mphrx.android.constants;

import com.mphrx.android.datasources.GoDetailDataSource;

import java.util.Arrays;
import java.util.List;
/**
 * Created by Shubham Goyal on 12-12-2015.
 * Constants to use through out the app
 */
public class Constants {
	public static final String PREF_NAME = "com.mphrx.android";

	public static float deviceDensityFactor;
	public static int deviceWidth;
	public static int deviceHeight;

    public interface DB {
        List<String> tables = Arrays.asList(GoDetailDataSource.GO_DETAIL_TABLE);
        int DB_VERSION = 1;
        String DB_NAME = "com.mphrx.android.db";
    }

	public interface URL {
        String BASE_URL						= "http://beta.json-generator.com/api/json/";

		String GoUrl 					    = BASE_URL + "get/415c8fMHl";
        String DetailUrl_1					= BASE_URL + "get/HjRRSIl";
		String DetailUrl_2					= BASE_URL + "get/VJ7L0YDVl";
    }
}
