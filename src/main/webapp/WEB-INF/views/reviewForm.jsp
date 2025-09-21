<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>AI Review Analysis</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background-color: #f0f4f8;
            margin: 0;
            padding: 50px 0;
            display: flex;
            justify-content: center;
        }
        .container {
            background-color: #fff;
            width: 90%;
            max-width: 700px;
            border-radius: 10px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.1);
            padding: 30px;
        }
        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }
        textarea {
            width: 100%;
            height: 120px;
            padding: 12px;
            border-radius: 8px;
            border: 1px solid #ddd;
            resize: none;
            font-size: 14px;
            transition: all 0.3s;
        }
        textarea:focus {
            border-color: #007bff;
            box-shadow: 0 0 5px rgba(0,123,255,0.3);
            outline: none;
        }
        input[type="submit"] {
            width: 100%;
            padding: 12px;
            margin-top: 15px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            cursor: pointer;
            transition: all 0.3s;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
        .result-box {
            margin-top: 25px;
            padding: 20px;
            border-radius: 10px;
            background-color: #f9f9f9;
            border-left: 6px solid #007bff;
        }
        .result-box h3 {
            margin-top: 0;
            color: #007bff;
        }
        .result-box p {
            font-size: 16px;
            margin: 8px 0;
        }
        .sentiment-positive { color: green; font-weight: bold; }
        .sentiment-negative { color: red; font-weight: bold; }
        .sentiment-neutral { color: orange; font-weight: bold; }
    </style>
</head>
<body>
    <div class="container">
        <h2>AI Review Analysis</h2>
        <form action="analyzeReviewForm" method="post">
            <textarea name="prompt" placeholder="Enter your review here..." required>${prompt}</textarea>
            <input type="submit" value="Analyze Review">
        </form>

        <c:if test="${not empty sentiment}">
            <div class="result-box">
                <h3>Analysis Result:</h3>
                <p><strong>Sentiment:</strong> 
                    <span class="<c:choose>
                                    <c:when test='${sentiment == "positive"}'>sentiment-positive</c:when>
                                    <c:when test='${sentiment == "negative"}'>sentiment-negative</c:when>
                                    <c:otherwise>sentiment-neutral</c:otherwise>
                                  </c:choose>">
                        ${sentiment}
                    </span>
                </p>
                <p><strong>Mood:</strong> ${mood}</p>
                <p><strong>Personality:</strong> ${personality}</p>
                <p><strong>Conclusion:</strong> ${conclusion}</p>
            </div>
        </c:if>
    </div>
</body>
</html>
