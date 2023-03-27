<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="result.js"></script>
</head>

<body onload="getAllVotes()" class="bg-gray-100">
    <div class="container mx-auto py-4">
        <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-4 gap-6">
            <div class="bg-white rounded-xl border border-blue-300 p-4 shadow-black-200 hover:bg-blue-200">
                <h4 class="text-lg text-center font-medium mb-4">Non Polling Details</h4>
                <table class="table-auto w-full text-center">
                    <tbody id="nonpoll"></tbody>
                </table>
            </div>
            <div class="bg-white rounded-xl border border-blue-300 p-4 shadow-md hover:bg-blue-200">
                <h4 class="text-lg text-center font-medium mb-4">GenderWise count Details</h4>
                <table class="table-auto w-full text-center">
                    <tbody id="gender"></tbody>
                </table>
            </div>
            <div class="bg-white rounded-xl border border-blue-300 p-4 shadow-md hover:bg-blue-200">
                <h4 class="text-lg text-center font-medium mb-4">Party count Details</h4>
                <table class="table-auto w-full text-center">
                    <tbody id="partyCounts"></tbody>
                </table>
            </div>
            <div class="bg-white rounded-xl border border-blue-300 p-4 shadow-md hover:bg-blue-200">
                <h4 class="text-lg text-center font-medium mb-4 ">WardWise Party count Details</h4>
                <table class="table-auto w-full text-center">
                    <tbody id="groupedVotesbyward"></tbody>
                </table>
            </div>
        </div>
        <div class="flex flex-wrap p-4">
            <div class="w-full md:w-1/4 p-2">
                <h2 class="text-center">Party Count</h2>
                <canvas id="partyChart"></canvas>
            </div>
            <div class="w-full md:w-1/4 p-2">
                <h2 class="text-center">Gender Wise Count</h2>
                <canvas id="genderChart"></canvas>
            </div>
            <div class="w-full md:w-1/4 p-2">
                <h2 class="text-center">Ward Wise Party Count</h2>
                <canvas id="myChart"></canvas>
            </div>
        </div>
    </div>
</body>

</html>
