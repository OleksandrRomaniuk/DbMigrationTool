package com.dbbest.databasemanager.loadingmanager.printers;

import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeSingleConstants;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.delete.IndexAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.tags.delete.TableCategoriesTagNameCategories;
import com.dbbest.databasemanager.loadingmanager.printers.mysql.IndexPrinterHelper;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Test;

import static org.junit.Assert.*;

public class IndexPrinterHelperTest {

    @Test
    public void execute() throws ContainerException {
        Container tableContainer = new Container();
        tableContainer.addAttribute(AttributeSingleConstants.TABLE_NAME, "inventory");
        Container indexCategory = new Container();
        indexCategory.setName(LoaderPrinterName.TABLE_INDEXES);
        tableContainer.addChild(indexCategory);

        Container index1 = new Container();
        index1.addAttribute(IndexAttributes.INDEX_NAME.getElement(), "PRIMARY");
        Container index11 = new Container();
        index1.addChild(index11);
        index11.addAttribute(IndexAttributes.INDEX_NAME.getElement(), "PRIMARY");
        index11.addAttribute(IndexAttributes.COLUMN_NAME.getElement(), "inventory_id");
        index11.addAttribute(IndexAttributes.SEQ_IN_INDEX.getElement(), "1");
        index11.addAttribute(IndexAttributes.SUB_PART.getElement(), "null");
        index11.addAttribute(IndexAttributes.INDEX_TYPE.getElement(), "BTREE");

        Container index2 = new Container();
        index2.addAttribute(IndexAttributes.INDEX_NAME.getElement(), "idx_fk_film_id");
        Container index21 = new Container();
        index2.addChild(index21);
        index21.addAttribute(IndexAttributes.INDEX_NAME.getElement(), "idx_fk_film_id");
        index21.addAttribute(IndexAttributes.COLUMN_NAME.getElement(), "film_id");
        index21.addAttribute(IndexAttributes.SEQ_IN_INDEX.getElement(), "1");
        index21.addAttribute(IndexAttributes.SUB_PART.getElement(), "20");
        index21.addAttribute(IndexAttributes.INDEX_TYPE.getElement(), "null");

        Container index3 = new Container();
        index3.addAttribute(IndexAttributes.INDEX_NAME.getElement(), "idx_store_id_film_id");
        Container index31 = new Container();
        index3.addChild(index31);
        index31.addAttribute(IndexAttributes.INDEX_NAME.getElement(), "idx_store_id_film_id");
        index31.addAttribute(IndexAttributes.COLUMN_NAME.getElement(), "store_id");
        index31.addAttribute(IndexAttributes.SEQ_IN_INDEX.getElement(), "2");
        index31.addAttribute(IndexAttributes.SUB_PART.getElement(), "10");
        index31.addAttribute(IndexAttributes.INDEX_TYPE.getElement(), "BTREE");

        Container index32 = new Container();
        index3.addChild(index32);
        index32.addAttribute(IndexAttributes.INDEX_NAME.getElement(), "idx_store_id_film_id");
        index32.addAttribute(IndexAttributes.COLUMN_NAME.getElement(), "film_id");
        index32.addAttribute(IndexAttributes.SEQ_IN_INDEX.getElement(), "1");
        index32.addAttribute(IndexAttributes.SUB_PART.getElement(), "15");
        index32.addAttribute(IndexAttributes.INDEX_TYPE.getElement(), "BTREE");

        indexCategory.addChild(index1);
        indexCategory.addChild(index2);
        indexCategory.addChild(index3);

        IndexPrinterHelper indexPrinterBuilder = new IndexPrinterHelper();

        assertEquals(indexPrinterBuilder.execute(tableContainer), "INDEX idx_fk_film_id (film_id (20)),\n" +
            "INDEX idx_store_id_film_id USING BTREE (film_id (15), store_id (10)),\n");
    }
}