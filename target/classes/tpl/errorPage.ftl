<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Error Page</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .card {
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border: none;
            border-radius: 12px;
        }
        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
        .alert {
            font-size: 1.2rem;
        }
    </style>
</head>
<body>

<div class="container py-4">
    <h1 class="text-center mb-4 text-danger"><i class="fas fa-exclamation-triangle"></i> Error</h1>

    <div class="card mb-4">
        <div class="card-body">
            <h3 class="card-title text-danger"><i class="fas fa-times-circle"></i> Something Went Wrong</h3>
            <p class="text-muted">We encountered an issue while processing your request. Please try again later or contact support if the problem persists.</p>
        </div>
    </div>

    <!-- Display custom error message -->
    <#if errorMessage??>
        <div class="alert alert-danger" role="alert">
            <i class="fas fa-exclamation-circle"></i> ${errorMessage}
        </div>
    <#else>
        <div class="alert alert-warning" role="alert">
            <i class="fas fa-info-circle"></i> No error message available.
        </div>
    </#if>

    <div class="text-center mt-4">
        <form action="home" method="GET" style="display: inline;">
            <button type="submit" class="btn btn-secondary"><i class="fas fa-arrow-left"></i> Back to Home</button>
        </form>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
