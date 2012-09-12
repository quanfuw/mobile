package org.exoplatform.session6phi.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PhiSQLiteHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "PHI_DICTIONARY";
	private static final int DATABASE_VERSION = 1;
    private static final String DICTIONARY_TABLE_NAME = "phi_session6_dict";
    private static final String DICTIONARY_TABLE_CREATE =
                "CREATE TABLE " + DICTIONARY_TABLE_NAME + " (" +
                "WORD_ID INT PRIMARY KEY, "+
                "WORD_WORD TEXT,"+
                "WORD_DEF TEXT,"+
                "WORD_SYN TEXT);";
    private static final String SELECT_WORDS = 
    		"SELECT WORD_WORD "+
            "FROM "+DICTIONARY_TABLE_NAME+" "+
            "WHERE WORD_WORD LIKE ? OR WORD_DEF LIKE ? "+
            "ORDER BY WORD_WORD ASC";
    
    private static final String SELECT_WORD =
    		"SELECT WORD_DEF "+
    		"FROM "+DICTIONARY_TABLE_NAME+" "+
    		"WHERE WORD_WORD=?";

    public PhiSQLiteHelper(Context c) {
    	super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DICTIONARY_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}
	
	public boolean createWord(String word, String desc, String synonyms) {
		
		ContentValues values = new ContentValues();
		values.put("WORD_WORD", word);
		values.put("WORD_DEF", desc);
		
		try {
			getWritableDatabase().insert(DICTIONARY_TABLE_NAME, null, values);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public List<String> searchWords(String likeWord) {
		List<String> words = new ArrayList<String>();
		try {
			Cursor c = getReadableDatabase().
			rawQuery(SELECT_WORDS,
				new String[]{"%"+likeWord+"%", "%"+likeWord+"%"});
			while (c.moveToNext()) {
				 words.add(c.getString(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return words;
	}
	
	public String getDefinitionOfWord(String word) {
		String def = null;
		try {
			Cursor c = getReadableDatabase().
					rawQuery(SELECT_WORD,
							new String[] {word});
			if (c.moveToNext()) {
				def = c.getString(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return def;
	}
}
