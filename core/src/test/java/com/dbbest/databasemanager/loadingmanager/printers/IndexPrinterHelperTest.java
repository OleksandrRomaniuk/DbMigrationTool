package com.dbbest.databasemanager.loadingmanager.printers;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.delete.IndexAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.tags.delete.TableCategoriesTagNameCategories;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Test;

import static org.junit.Assert.*;

public class IndexPrinterHelperTest {

    @Test
    public void execute() throws ContainerException {
        Container tableContainer = new Container();
        tableContainer.setName("inventory");
        Container indexCategory = new Container();
        indexCategory.setName(TableCategoriesTagNameCategories.Indexes.getElement());
        tableContainer.addChild(indexCategory);

        Container index1 = new Container();
        indexCategory.addChild(index1);
        index1.addAttribute(IndexAttributes.INDEX_NAME.getElement(), "PRIMARY");
        index1.addAttribute(IndexAttributes.COLUMN_NAME.getElement(), "inventory_id");
        index1.addAttribute(IndexAttributes.SEQ_IN_INDEX.getElement(), "1");
        index1.addAttribute(IndexAttributes.SUB_PART.getElement(), "null");
        index1.addAttribute(IndexAttributes.INDEX_TYPE.getElement(), "BTREE");

        Container index2 = new Container();
        indexCategory.addChild(index2);
        index2.addAttribute(IndexAttributes.INDEX_NAME.getElement(), "idx_fk_film_id");
        index2.addAttribute(IndexAttributes.COLUMN_NAME.getElement(), "film_id");
        index2.addAttribute(IndexAttributes.SEQ_IN_INDEX.getElement(), "1");
        index2.addAttribute(IndexAttributes.SUB_PART.getElement(), "20");
        index2.addAttribute(IndexAttributes.INDEX_TYPE.getElement(), "null");

        Container index3 = new Container();
        indexCategory.addChild(index3);
        index3.addAttribute(IndexAttributes.INDEX_NAME.getElement(), "idx_store_id_film_id");
        index3.addAttribute(IndexAttributes.COLUMN_NAME.getElement(), "store_id");
        index3.addAttribute(IndexAttributes.SEQ_IN_INDEX.getElement(), "2");
        index3.addAttribute(IndexAttributes.SUB_PART.getElement(), "10");
        index3.addAttribute(IndexAttributes.INDEX_TYPE.getElement(), "BTREE");

        Container index4 = new Container();
        indexCategory.addChild(index4);
        index4.addAttribute(IndexAttributes.INDEX_NAME.getElement(), "idx_store_id_film_id");
        index4.addAttribute(IndexAttributes.COLUMN_NAME.getElement(), "film_id");
        index4.addAttribute(IndexAttributes.SEQ_IN_INDEX.getElement(), "1");
        index4.addAttribute(IndexAttributes.SUB_PART.getElement(), "15");
        index4.addAttribute(IndexAttributes.INDEX_TYPE.getElement(), "BTREE");

        IndexPrinterHelper indexPrinterBuilder = new IndexPrinterHelper();

        assertEquals(indexPrinterBuilder.execute(tableContainer), "INDEX PRIMARY USING BTREE (inventory_id)\n" +
            "INDEX idx_fk_film_id (film_id (20))\n" +
            "INDEX idx_store_id_film_id USING BTREE (film_id (15), store_id (10))\n");
    }
}