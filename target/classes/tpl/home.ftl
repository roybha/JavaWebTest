<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Interaction</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            max-width: 600px;
            margin-top: 80px;
        }
        .form-section {
            margin-bottom: 30px;
        }
        .heading {
            font-weight: bold;
        }
    </style>
</head>
<body>

<div class="container">
    <h1 class="text-center text-primary mb-4">Discover new acquaintances!</h1>
    <p class="text-center mb-4">Find your second part</p>

    <!-- Повідомлення про помилку або успіх -->
    <#if message??>
        <p class="text-center text-danger">${message}</p>
    </#if>

    <!-- Форма пошуку -->
    <div class="form-section text-center">
        <form action="users" method="POST">
            <button type="submit" class="btn btn-primary btn-lg w-100">Search for Users</button>
        </form>
    </div>

    <!-- Форма введення ID користувача -->
    <div class="form-section">
        <form action="messages" method="GET" class="d-flex flex-column align-items-center">
            <label for="id" class="form-label heading mb-2">Enter User ID:</label>
            <div class="d-flex align-items-center w-100">
                <input type="number" id="id" name="id" class="form-control w-75 me-2" min="1" required>
                <button type="submit" class="btn btn-success">Go chat with sb</button>
            </div>
        </form>
    </div>

    <!-- Форма для виходу -->
    <div class="form-section text-center">
        <form action="exit" method="POST">
            <button type="submit" class="btn btn-danger btn-lg w-100">Exit</button>
        </form>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

