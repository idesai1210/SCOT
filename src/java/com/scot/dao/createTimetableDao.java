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
 * @author Nisarg
 */
public class createTimetableDao {

    private static Connection con = null;
    private static Statement stmt = null;

    public static List<TableBean> getSubjectList(TableBean bean) {

        List<TableBean> subjectList = new ArrayList<>();

        try {
            con = ConnectionUtil.getCon();
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select * from subject where sem_id = '" + bean.getSem_id() + "'");

            while (rs.next()) {
                TableBean tableBean = new TableBean();

                tableBean.setSubject_id(rs.getInt("subject_id"));
                tableBean.setSubject_name(rs.getString("subject_name"));
                tableBean.setCredit(rs.getInt("credit"));

                subjectList.add(tableBean);

            }
            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in Stmt " + e);
        }
        return subjectList;
    }

    public static List<TableBean> getFacultyList(List<TableBean> subjectList) {

        List<TableBean> allocatedFacultyList = new ArrayList<>();
        TableBean bean;

        try {
            con = ConnectionUtil.getCon();
            stmt = con.createStatement();
            for (int i = 0; i < subjectList.size(); i++) {

                bean = new TableBean();
                bean = subjectList.get(i);

                ResultSet rs = stmt.executeQuery("select f.*,s.* from faculty_allocation_subject f,subject s where s.subject_id = '" + bean.getSubject_id() + "' AND f.subject_id = '" + bean.getSubject_id() + "' ");

                while (rs.next()) {
                    TableBean tableBean = new TableBean();

                    tableBean.setFaculty_allocation_subject_id(rs.getInt("faculty_allocation_subject_id"));
                    tableBean.setSubject_id(rs.getInt("subject_id"));
                    tableBean.setRegistration_id(rs.getInt("registration_id"));
                    tableBean.setSubject_name(rs.getString("subject_name"));
                    tableBean.setCredit(rs.getInt("credit"));

                    allocatedFacultyList.add(tableBean);
                }
            }

            int x = allocatedFacultyList.size();

            for (int i = 0; i < x; i++) {

                bean = new TableBean();
                bean = allocatedFacultyList.get(i);

                ResultSet rs = stmt.executeQuery("select * from registration_detail_user where registration_id = '" + bean.getRegistration_id() + "' ");

                while (rs.next()) {
                    bean.setDepartment_id(rs.getInt("department_id"));
                    bean.setUser_type_id(rs.getInt("user_type_id"));
                    bean.setfName(rs.getString("f_name"));
                    bean.setlName(rs.getString("l_name"));
                }
            }

            for (int i = 0; i < x; i++) {

                bean = new TableBean();
                bean = allocatedFacultyList.get(i);

                ResultSet rs = stmt.executeQuery("select * from faculty_workload where user_type_id = '" + bean.getUser_type_id() + "'  AND department_id = '" + bean.getDepartment_id() + "' ");

                while (rs.next()) {

                    bean.setWorkload(rs.getInt("workload"));
                }
            }

            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in Stmt " + e);
        }
        return allocatedFacultyList;

    }

    public static List<TableBean> getclassHoursDetail(int department_id) {

        List<TableBean> classHoursList = new ArrayList<>();
        TableBean bean;

        try {
            con = ConnectionUtil.getCon();
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select * from degree where department_id = '" + department_id + "'");

            while (rs.next()) {
                TableBean tableBean = new TableBean();

                tableBean.setDegree_id(rs.getInt("degree_id"));
                classHoursList.add(tableBean);

            }

            int x = classHoursList.size();

            for (int i = 0; i < x; i++) {

                bean = new TableBean();
                bean = classHoursList.get(i);

                rs = stmt.executeQuery("select * from class_type_hours where degree_id = '" + bean.getDegree_id() + "' ");
                classHoursList.remove(i);

                while (rs.next()) {

                    bean = new TableBean();
                    bean.setDegree_id(rs.getInt("degree_id"));
                    bean.setClass_type_id(rs.getInt("class_type_id"));
                    bean.setHours(rs.getInt("hours"));
                    classHoursList.add(bean);
                }
            }

            x = x = classHoursList.size();

            for (int i = 0; i < x; i++) {

                bean = new TableBean();
                bean = classHoursList.get(i);

                rs = stmt.executeQuery("select * from class_type where class_type_id = '" + bean.getClass_type_id() + "' ");

                while (rs.next()) {

                    bean.setClass_type(rs.getString("class_type"));

                }
            }

            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in Stmt " + e);
        }

        return classHoursList;
    }

    public static List<TableBean> getUsedWorkload(int sem_id, List<TableBean> allocatedFacultyList) {

        List<TableBean> usedWorkload = new ArrayList<>();
        TableBean bean, bean1;
        try {
            con = ConnectionUtil.getCon();
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select * from division where sem_id = '" + sem_id + "'");

            while (rs.next()) {
                int y = rs.getInt("division_id");

                for (int i = 0; i < allocatedFacultyList.size(); i++) {
                    bean = new TableBean();
                    bean = allocatedFacultyList.get(i);
                    rs = stmt.executeQuery("select t.*,f.* from timetable_main t,faculty_allocation_subject f where t.div_id = '" + y + "' and t.faculty_allocation_subject_id = '" + bean.getFaculty_allocation_subject_id() + "' and f.faculty_allocation_subject_id = '" + bean.getFaculty_allocation_subject_id() + "' ");

                    while (rs.next()) {

                        TableBean beanTemp = new TableBean();

                        beanTemp.setFaculty_allocation_subject_id(rs.getInt("faculty_allocation_subject_id"));
                        beanTemp.setRegistration_id(rs.getInt("registration_id"));

                        usedWorkload.add(beanTemp);
                    }

                }
            }

            for (int i = 0; i < usedWorkload.size(); i++) {

                bean = new TableBean();
                bean = usedWorkload.get(i);

                for (int j = 0; j < allocatedFacultyList.size(); j++) {

                    bean1 = new TableBean();
                    bean1 = allocatedFacultyList.get(j);
                    int x = bean1.getWorkload();

                    if (bean.getRegistration_id() == bean1.getRegistration_id()) {

                        --x;
                        bean1.setWorkload(x);
                    }
                }
            }

            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in Stmt " + e);
        }

        for (int i = 0; i < allocatedFacultyList.size(); i++) {
            bean = new TableBean();
            bean = allocatedFacultyList.get(i);
        }

        return usedWorkload;
    }

    public static int finalEntryMainTimeTable() {
        int no = 0;

        try {
            con = ConnectionUtil.getCon();
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select * from timetable_main ORDER BY timetable_main_id DESC LIMIT 1");

            rs.next();

            no = rs.getInt("timetable_id");
            no = no + 1;

            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in Stmt " + e);
        }

        return no;
    }

    public static void insertTimetablemain(TableBean bean, int totalHourSlot, String[] FASI, int[] startTime, int[] endTime) {

        try {
            con = ConnectionUtil.getCon();
            stmt = con.createStatement();

            for (int i = 1; i <= totalHourSlot; i++) {

                if ("0".equals(FASI[i])) {
                    FASI[i] = "7";
                }
                if ("blank".equals(FASI[i])) {
                    FASI[i] = "8";
                }
                //  System.out.println("insert into timetable_main values(null,'" + bean.getTimetable_id() + "','" + bean.getDay_id() + "','" + startTime[i] + "','" + endTime[i] + "','" + bean.getDiv_id() + "','" + FASI[i] + "')");

                stmt.executeUpdate("insert into timetable_main values(null,'" + bean.getTimetable_id() + "','" + bean.getDay_id() + "','" + startTime[i] + "','" + endTime[i] + "','" + bean.getDiv_id() + "','" + FASI[i] + "')");
            }

            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in Stmt " + e);
        }
    }

    public static void entryTimetableTrack(int div_id, int timetable_id, int start_time, int total_duration) {
        try {
            con = ConnectionUtil.getCon();
            stmt = con.createStatement();

            stmt.executeUpdate("insert into timetable_track values('" + timetable_id + "','" + div_id + "','" + start_time + "','" + total_duration + "')");

            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in Stmt " + e);
        }
    }

    public static List<UserBean> divTimetable(int div_id) {

        List<UserBean> divTimeTable = new ArrayList<>();

        UserBean userBean;

        try {
            con = ConnectionUtil.getCon();
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select * from timetable_track where div_id = '" + div_id + "'");

            while (rs.next()) {
                userBean = new UserBean();
                userBean.setTimetable_track_id(rs.getInt("timetable_track_id"));
                userBean.setStart_time(rs.getInt("start_time"));
                userBean.setTotal_duration(rs.getInt("total_duration"));

                divTimeTable.add(userBean);
            }

            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in Stmt " + e);
        }

        return divTimeTable;

    }

    public static List<UserBean> divTimetableDetail(int timetable_id) {
        List<UserBean> divTimeTableDetail = new ArrayList<>();

        UserBean userBean = new UserBean();

        try {
            con = ConnectionUtil.getCon();
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select * from timetable_track where timetable_track_id = '" + timetable_id + "'");

            rs.next();

            userBean.setStart_time(rs.getInt("start_time"));
            userBean.setTotal_duration(rs.getInt("total_duration"));

            divTimeTableDetail.add(userBean);

            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in Stmt " + e);
        }

        return divTimeTableDetail;
    }

    public static List<TableBean> loadTimeTableMain(int timetable_id) {

        List<TableBean> timetable = new ArrayList<>();

        try {
            con = ConnectionUtil.getCon();
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select * from timetable_main where timetable_id = '" + timetable_id + "'");
            TableBean tableBean;

            while (rs.next()) {
                tableBean = new TableBean();
                tableBean.setDay_id(rs.getInt("day_id"));
                tableBean.setStart(rs.getString("start_time"));
                tableBean.setEnd(rs.getString("end_time"));
                tableBean.setFaculty_allocation_subject_id(rs.getInt("faculty_allocation_subject_id"));

                timetable.add(tableBean);
            }

            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in Stmt " + e);
        }

        return timetable;
    }

    public static List<TableBean> totalDays(int timetable_id) {
        List<TableBean> days = new ArrayList<>();

        try {
            con = ConnectionUtil.getCon();
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select distinct day_id from timetable_main where timetable_id='" + timetable_id + "'");
            TableBean tableBean;

            while (rs.next()) {
                tableBean = new TableBean();
                tableBean.setDays(rs.getInt("day_id"));

                days.add(tableBean);
            }

            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in Stmt " + e);
        }

        return days;
    }
}
