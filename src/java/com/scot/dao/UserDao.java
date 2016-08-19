/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scot.dao;

import com.scot.bean.TableBean;
import com.scot.bean.UserBean;
import com.scot.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Anand
 */
public class UserDao {

    private static Connection con = null;
    private static Statement stmt = null;

    public static int loginUser(UserBean userBean) {
        int check = 0; // no data

        try {
            con = ConnectionUtil.getCon();
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("Select * from registration_detail_user where e_mail='" + userBean.getEmail() + "' And password='" + userBean.getPassWord() + "'");

            while (rs.next()) {
                check = 1; // data but not active
                if (rs.getBoolean("isActive")) {
                    check = 2; // data and active
                }
            }
            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in Stmt " + e);
        }

        return check;
    }

    public static UserBean userDetail(UserBean userBean) {

        try {
            con = ConnectionUtil.getCon();
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("Select * from registration_detail_user where e_mail='" + userBean.getEmail() + "' And password='" + userBean.getPassWord() + "'");

            rs.next();

            userBean.setRegistration_id(rs.getInt("registration_id"));
            userBean.setUser_type_id(rs.getInt("user_type_id"));
            userBean.setEmail(rs.getString("e_mail"));
            userBean.setfName(rs.getString("f_name"));
            userBean.setmName(rs.getString("m_name"));
            userBean.setlName(rs.getString("l_name"));
            userBean.setGender(rs.getString("gender"));
            userBean.setDateOfBirth(rs.getDate("dob"));
            userBean.setMobileNo(rs.getLong("phone_no"));
            userBean.setAddress(rs.getString("address"));
            userBean.setCity(rs.getString("city"));
            userBean.setDegree_registration(rs.getString("degree"));
            userBean.setProfessionalDetail(rs.getString("professional_detail"));
            userBean.setDepartment_id(rs.getInt("department_id"));
            userBean.setIsActive(rs.getBoolean("isActive"));
            userBean.setPassWord(rs.getString("password"));
            userBean.setInstitute_id(rs.getInt("institute_id"));
            userBean.setState(rs.getString("state"));
            userBean.setCountry_id(rs.getInt("country_id"));

            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in Stmt " + e);
        }

        return userBean;
    }

    public static void insertRegistrationData(UserBean userBean) {

        try {
            con = ConnectionUtil.getCon();
            stmt = con.createStatement();

            stmt.executeUpdate("insert into registration_detail_user values (null,'" + userBean.getUser_type_id() + "','" + userBean.getEmail() + "','" + userBean.getfName() + "','" + userBean.getmName() + "','" + userBean.getlName() + "','" + userBean.getGender() + "','" + userBean.getDateOfBirth() + "','" + userBean.getMobileNo() + "','" + userBean.getAddress() + "','" + userBean.getCity() + "','" + userBean.getDegree_registration() + "','" + userBean.getProfessionalDetail() + "','" + userBean.getDepartment_id() + "',false,'" + userBean.getPassWord() + "','" + userBean.getInstitute_id() + "','" + userBean.getState() + "','" + userBean.getCountry_id() + "')");

            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in Stmt " + e);
        }

    }

    public static void updateRegistrationData(UserBean userBean) {

        try {
            con = ConnectionUtil.getCon();
            stmt = con.createStatement();

            stmt.executeUpdate("update registration_detail_user set phone_no = '" + userBean.getMobileNo() + "', city = '" + userBean.getCity() + "', degree = '" + userBean.getDegree_registration() + "', professional_detail = '" + userBean.getProfessionalDetail() + "',department_id = '" + userBean.getDepartment_id() + "', password = '" + userBean.getPassWord() + "', institute_id = '" + userBean.getInstitute_id() + "', state = '" + userBean.getState() + "', country_id = '" + userBean.getCountry_id() + "', address = '" + userBean.getAddress() + "' where registration_id = '" + userBean.getRegistration_id() + "'");

            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in Stmt " + e);
        }
    }

    public static List<TableBean> notificationShow(int i) {

        List<TableBean> notificationList = new ArrayList<>();
        TableBean tableBean;
        ResultSet rs;

        try {
            con = ConnectionUtil.getCon();
            stmt = con.createStatement();

            if (i == 1) {
                rs = stmt.executeQuery("select * from registration_detail_user where user_type_id=2 AND isActive=false");
            } else {
                rs = stmt.executeQuery("select * from registration_detail_user where user_type_id<>2 AND user_type_id<>1 AND isActive=false");
            }

            while (rs.next()) {
                tableBean = new TableBean();
                tableBean.setRegistration_id(rs.getInt("registration_id"));
                tableBean.setEmail(rs.getString("e_mail"));
                tableBean.setfName(rs.getString("f_name"));
                tableBean.setmName(rs.getString("m_name"));
                tableBean.setlName(rs.getString("l_name"));
                tableBean.setGender(rs.getString("gender"));
                tableBean.setDateOfBirth(rs.getDate("dob"));
                tableBean.setDepartment_id(rs.getInt("department_id"));
                tableBean.setInstitute_id(rs.getInt("institute_id"));

                notificationList.add(tableBean);
            }

            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in Stmt " + e);
        }

        return notificationList;
    }

    public static List<TableBean> institute_detailShow() {
        List<TableBean> instituteList = new ArrayList<>();

        try {
            con = ConnectionUtil.getCon();
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select * from institute_detail");

            while (rs.next()) {
                TableBean tableBean = new TableBean();

                tableBean.setInstitute_id(rs.getInt("institute_detail_id"));
                tableBean.setInstitute_name(rs.getString("institute_name"));
                instituteList.add(tableBean);

            }
            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in Stmt " + e);
        }

        return instituteList;
    }

    public static List<TableBean> select_department(int institute_id) {

        List<TableBean> departmentList = new ArrayList();

        try {
            con = ConnectionUtil.getCon();
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select d.*,i.* from department_detail d,institute_detail i where i.institute_detail_id = d.institute_id and d.institute_id ='" + institute_id + "' ");

            while (rs.next()) {
                TableBean tableBean = new TableBean();

                tableBean.setDepartment_id(rs.getInt("department_detail_id"));
                tableBean.setDepartment_name(rs.getString("department_name"));

                departmentList.add(tableBean);

            }
            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in Stmt " + e);
        }

        return departmentList;
    }

    public static List<TableBean> select_degree(int department_id) {

        List<TableBean> degreeList = new ArrayList();

        try {
            con = ConnectionUtil.getCon();
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select d.*,i.* from degree d,department_detail i where i.department_detail_id = d.department_id and d.department_id ='" + department_id + "' ");

            while (rs.next()) {
                TableBean tableBean = new TableBean();

                tableBean.setDegree_id(rs.getInt("degree_id"));
                tableBean.setDegree_name(rs.getString("degree_name"));

                degreeList.add(tableBean);

            }
            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in Stmt " + e);
        }

        return degreeList;
    }

    public static List<TableBean> select_semester(int degree_id) {

        List<TableBean> degreeList = new ArrayList();

        try {
            con = ConnectionUtil.getCon();
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select s.*,d.* from semester s,degree d where d.degree_id = s.degree_id and s.degree_id ='" + degree_id + "' ");

            while (rs.next()) {
                TableBean tableBean = new TableBean();

                tableBean.setSem_id(rs.getInt("semester_id"));
                tableBean.setSem_no(rs.getString("sem_no"));

                degreeList.add(tableBean);

            }
            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in Stmt " + e);
        }

        return degreeList;
    }

    public static List<TableBean> select_division(int semester_id) {

        List<TableBean> divisionList = new ArrayList();
        try {
            con = ConnectionUtil.getCon();
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select d.*,s.* from division d,semester s where d.sem_id = s.semester_id and d.sem_id ='" + semester_id + "' ");

            while (rs.next()) {
                TableBean tableBean = new TableBean();

                tableBean.setDiv_id(rs.getInt("division_id"));
                tableBean.setDiv_name(rs.getString("div_name"));
                
                divisionList.add(tableBean);

            }
            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in Stmt " + e);
        }

        TableBean bean;
        for (int i = 0; i < divisionList.size(); i++) {
            bean = new TableBean();
            bean = divisionList.get(i);
        }

        return divisionList;
    }
}
