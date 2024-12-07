<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration Page</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .form-container {
            max-width: 500px;
            margin: 50px auto;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        .form-title {
            text-align: center;
            margin-bottom: 20px;
        }
        .btn-back {
            margin-top: 10px;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="form-container">
        <h1 class="form-title text-primary">Create an Account</h1>
        <p class="text-center">Fill out the form below to register.</p>

        <!-- Повідомлення про помилку або успіх -->
        <#if message?? && (message?trim != "")>
            <div class="alert alert-danger text-center">${message}</div>
        </#if>

        <!-- Форма реєстрації через POST -->
        <form action="registration" method="POST">
            <div class="mb-3">
                <label for="name" class="form-label">Name</label>
                <input type="text" id="name" name="name" class="form-control" required>
            </div>
            <div class="mb-3">
                <label for="age" class="form-label">Age</label>
                <input type="number" id="age" name="age" class="form-control" min="1">
            </div>
            <div class="mb-3">
                <label class="form-label">Gender</label>
                <div>
                    <input type="radio" id="man" name="gender" value="man" required>
                    <label for="man">Male</label>
                    <input type="radio" id="woman" name="gender" value="woman">
                    <label for="woman">Female</label>
                </div>
            </div>
            <div class="mb-3">
                <label for="file" class="form-label">Profile Avatar (URL)</label>
                <input type="url" id="file" name="file" class="form-control" placeholder="Enter image URL" pattern="https?://.*" required>
            </div>
            <div class="mb-3">
                <label for="login" class="form-label">Login</label>
                <input type="text" id="login" name="login" class="form-control" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" id="password" name="password" class="form-control" required>
            </div>
            <div class="mb-3">
                <label for="confirm-password" class="form-label">Confirm Password</label>
                <input type="password" id="confirm-password" name="confirm-password" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-primary w-100">Register</button>
        </form>
        <form action="login" method="GET" class="text-center">
            <button type="submit" class="btn btn-secondary btn-back">Back</button>
        </form>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

