package com.kgisl.OnlineVotingSystem;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

@WebServlet("/polling")
public class PollingServlet extends HttpServlet {
    private PollingDAO pollingDAO;
    static String jdbcURL, jdbcUsername, jdbcPassword;

    @Override
    public void init() throws ServletException {
        String jdbcURL = "jdbc:mysql://localhost:3306/votingsystem";
        String jdbcUsername = "root";
        String jdbcPassword = "";
        pollingDAO = PollingDAO.getInstance(jdbcURL, jdbcUsername, jdbcPassword);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Polling> pollingList = new ArrayList<Polling>();
        try {
            pollingList = pollingDAO.listAllPollings();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        long l = pollingList.stream().map(e -> e.getVoter_id()).count();
        System.out.println(l);
        Map<String, Long> m = pollingList.stream()
                .collect(Collectors.groupingBy(Polling::getParty_name, Collectors.counting()));
        m.entrySet().stream().forEach(e -> System.out.println(e.getKey() + "" + e.getValue()));
        Map<String, Map<String, Long>> groupedVotesbyward = pollingList.stream().collect(Collectors.groupingBy(Polling::getWard,Collectors.groupingBy(Polling::getParty_name, Collectors.counting())));
        groupedVotesbyward.entrySet().stream().forEach(e->System.out.println(e.getKey()+""+e.getValue()));
        // Gson gson = new Gson();
        // String jsonString = gson.toJson(m);
        // resp.setContentType("application/json");
        // resp.setCharacterEncoding("UTF-8");
        // resp.getWriter().write(jsonString);

        String json = new Gson().toJson(pollingList);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestData = req.getReader().lines().collect(Collectors.joining());
        Polling newPolling = new Gson().fromJson(requestData, Polling.class);
        System.out.println("requestData Length->" + requestData.length());
        System.out.println("requestData->" + requestData);
        try {
            pollingDAO.insertPollings(newPolling);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Polling data saved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error while saving polling data.");
        }
    }
    // super.doPost(req, resp);
}
