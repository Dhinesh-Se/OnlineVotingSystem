package com.kgisl.OnlineVotingSystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/result")
public class ResultServlet extends HttpServlet {
    private PollingDAO pollingDAO;
    private VoterDAO voterDAO;

    @Override
    public void init() throws ServletException {
        voterDAO = VoterDAO.getInstance("jdbc:mysql://localhost:3306/votingsystem", "root", "");
        pollingDAO = PollingDAO.getInstance("jdbc:mysql://localhost:3306/votingsystem", "root", "");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Voter> voterList = voterDAO.listAllVoters();
            List<Polling> pollingList = pollingDAO.listAllPollings();
            List<VoterAndPolling> voterAndPollingList = new ArrayList<>();
            pollingList.stream()
                    .flatMap(p -> voterList.stream()
                            .filter(v -> p.getVoter_id().equals(v.getVoter_id()))
                            .map(v -> new VoterAndPolling(p.getVoter_id(), v.getName(), null, null, v.getAge(),
                                    v.getGender(), v.getWard(), p.getParty_name(),p.getElection_name(),p.getElection_date())))
                    .forEach(voterAndPollingList::add);
            // for (Polling p : pollingList) {
            // for (Voter v : voterList) {
            // if (p.getVoter_id().equals(v.getVoter_id())) {
            // VoterAndPolling vp = new VoterAndPolling(p.getVoter_id(), v.getName(),
            // v.getEmail(),
            // v.getPassword(), v.getAge(), v.getGender(), p.getWard(), p.getParty_name());
            // voterAndPollingList.add(vp);
            // }
            // }
            // }
            // non polling list
            List<Voter> nonPollingList = voterList.stream()
                    .filter(voter -> pollingList.stream()
                            .noneMatch(polling -> polling.getVoter_id().equals(voter.getVoter_id())))
                    .sorted(Comparator.comparing(Voter::getVoter_id))
                    .collect(Collectors.toList());
            // group by party count
            Map<String, Long> partyCount = voterAndPollingList.stream()
            .collect(Collectors.groupingBy(VoterAndPolling::getParty_name, Collectors.counting()))
            .entrySet()
            .stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (e1, e2) -> e1, LinkedHashMap::new));
            // partyCount.entrySet().stream().sorted((Map.Entry.comparingByValue(Comparator.reverseOrder()))).forEach(e
            // -> System.out.println(e.getKey()+ " " +e.getValue()));
            // genderCount
            Map<String, Long> genderCount = voterAndPollingList.stream()
                    .collect(Collectors.groupingBy(VoterAndPolling::getGender, Collectors.counting()))
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (e1, e2) -> e1, LinkedHashMap::new));

            // group by ward
            Map<String, Map<String, Long>> groupedVotesbyward = voterAndPollingList.stream()
                    .collect(Collectors.groupingBy(VoterAndPolling::getWard,
                            Collectors.groupingBy(VoterAndPolling::getParty_name, Collectors.counting())))
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> e.getValue().entrySet().stream()
                                    .sorted(Map.Entry.<String, Long>comparingByValue()
                                            .reversed())
                                    .collect(Collectors.toMap(
                                            Map.Entry::getKey,
                                            Map.Entry::getValue,
                                            (v1, v2) -> v1,
                                            LinkedHashMap::new)),
                            (m1, m2) -> m1,
                            LinkedHashMap::new));
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("nonPollingDetails", nonPollingList);
            responseData.put("partyCounts", partyCount);
            responseData.put("genderCount", genderCount);
            responseData.put("groupedVotesbyward", groupedVotesbyward);
            String json = new Gson().toJson(responseData);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(json);
        } catch (Exception e) {

        }
    }
}
