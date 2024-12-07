<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Authorization Page</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            max-width: 400px;
            margin-top: 100px;
        }
    </style>
</head>
<body>

<div class="container">
    <h1 class="text-center text-primary mb-4">Welcome!</h1>
    <p class="text-center mb-4">Please log in to continue</p>

    <!-- Форма авторизації -->
    <form action="login" method="GET" class="mb-4">
        <div class="mb-3">
            <label for="login" class="form-label">Username:</label>
            <input type="text" id="login" name="login" class="form-control" required>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Password:</label>
            <input type="password" id="password" name="password" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-primary w-100">Log In</button>
    </form>

    <!-- Повідомлення про помилку -->
    <#if message?? && (message?trim != "")>
        <p class="text-center text-danger">${message}</p>
    </#if>

    <!-- Кнопка для реєстрації -->
    <form action="registration" method="GET" style="display: inline;">
        <button type="submit" class="btn btn-secondary w-100">Register</button>
    </form>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

