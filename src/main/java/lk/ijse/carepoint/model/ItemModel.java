package lk.ijse.carepoint.model;

import lk.ijse.carepoint.db.DbConnection;
import lk.ijse.carepoint.dto.ItemDto;
import lk.ijse.carepoint.dto.tm.cartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModel {
    public static boolean updateItem(List<cartTm> cartTmList) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE item SET description = ?, unit_price = ?, qty_on_hand = ? WHERE item_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

       /* pstm.setString(1, ItemDto.getDescription());
        pstm.setDouble(2, ItemDto.getUnitPrice());
        pstm.setInt(3, ItemDto.getQtyOnHand());
        pstm.setString(4, ItemDto.getCode());*/
        return pstm.executeUpdate() > 0;
    }

    public boolean saveItem(ItemDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO item VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getCode());
        pstm.setString(2, dto.getDescription());
        pstm.setDouble(3, dto.getUnitPrice());
        pstm.setInt(4, dto.getQtyOnHand());

        return pstm.executeUpdate() > 0;
    }

    public ItemDto searchItem(String code) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM item WHERE item_id = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, code);

        ResultSet resultSet = pstm.executeQuery();

        ItemDto dto = null;

        if(resultSet.next()) {
            dto = new ItemDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            );
        }
        return dto;
    }

    public boolean updateItem(ItemDto itemDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE item SET description = ?, unit_price = ?, qty_on_hand = ? WHERE item_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, itemDto.getDescription());
        pstm.setDouble(2, itemDto.getUnitPrice());
        pstm.setInt(3, itemDto.getQtyOnHand());
        pstm.setString(4, itemDto.getCode());

        return pstm.executeUpdate() > 0;
    }

    public List<ItemDto> loadAllItems() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM item";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<ItemDto> itemList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            itemList.add(new ItemDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            ));
        }

        return itemList;
    }

    public boolean deleteItem(String code) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM item WHERE item_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, code);

        return pstm.executeUpdate() > 0;
    }
}
