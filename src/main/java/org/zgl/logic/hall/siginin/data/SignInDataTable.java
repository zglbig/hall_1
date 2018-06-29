package org.zgl.logic.hall.siginin.data;

import org.zgl.utils.builder_clazz.ann.DataTable;
import org.zgl.utils.builder_clazz.excel_init_data.DataTableMessage;
import org.zgl.utils.builder_clazz.excel_init_data.StaticConfigMessage;

@DataTable
public class SignInDataTable implements DataTableMessage {
    private final int id;
    private final long gold;
    private final int changeCard;

    public SignInDataTable() {
        id = 0;
        gold = 0L;
        changeCard = 0;
    }
    public static SignInDataTable get(int id){
        return StaticConfigMessage.getInstance().get(SignInDataTable.class,id);
    }
    public int getId() {
        return id;
    }

    public long getGold() {
        return gold;
    }

    public int getChangeCard() {
        return changeCard;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public void AfterInit() {

    }
}
