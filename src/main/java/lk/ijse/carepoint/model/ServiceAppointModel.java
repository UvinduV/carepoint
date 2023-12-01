package lk.ijse.carepoint.model;

import lk.ijse.carepoint.db.DbConnection;
import lk.ijse.carepoint.dto.serviceAppointDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceAppointModel {
    public static String generateNextAppointId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT appoint_id FROM appointment ORDER BY appoint_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitAppointId(resultSet.getString(1));
        }
        return splitAppointId(null);
    }

    private static String splitAppointId(String currentAppointId) {
        if(currentAppointId != null) {
            String[] split = currentAppointId.split("A0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "A00" + id;
        } else {
            return "A001";
        }
    }

    public static boolean saveAppoint(serviceAppointDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO appointment VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getAppoint_Id());
        pstm.setString(2, dto.getCust_Id());
        pstm.setString(3, dto.getVehicle_No());
        pstm.setString(4, dto.getShedule_Id());
        pstm.setString(5, dto.getPackage_Id());
       // pstm.setString(6, dto.getName());
        pstm.setString(7, String.valueOf(dto.getDate()));
        pstm.setString(8, dto.getTime());
        pstm.setString(9, String.valueOf(dto.getAmount()));

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }
}
