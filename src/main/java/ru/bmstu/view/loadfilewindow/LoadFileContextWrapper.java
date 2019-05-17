package ru.bmstu.view.loadfilewindow;

import ru.bmstu.parsingexcel.MetadataExcelSheet;
import ru.bmstu.view.utils.Context;

import java.util.List;

public class LoadFileContextWrapper {
    private static String SHEET_META_CONTEXT_KEY = "SelectFile_SheetsMeta";

    public static void addExcelMeta(List<MetadataExcelSheet> sheetsMeta) {
        Context.addContextObject(SHEET_META_CONTEXT_KEY, sheetsMeta);
    }

    @SuppressWarnings("unchecked")
    public static  List<MetadataExcelSheet> getExcelMeta() {
        return (List<MetadataExcelSheet>)Context.getContextObject(SHEET_META_CONTEXT_KEY);
    }

    public static void clearContext(){
        Context.removeContextObject(SHEET_META_CONTEXT_KEY);
    }
}
