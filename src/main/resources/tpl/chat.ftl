<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Chat Page</title>
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
    </style>
</head>
<body>

<div class="container py-4">
    <h1 class="text-center mb-4 text-primary"><i class="fas fa-comments"></i> Chat with User</h1>

    <#if user??>
        <div class="card mb-4">
            <div class="card-body">
                <h2 class="card-title text-info"><i class="fas fa-user"></i> User Information</h2>
                <p><strong>Name:</strong> ${user.name}</p>
                <p><strong>Age:</strong> ${user.age}</p>
                <div class="text-center">
                    <img src="${user.file}" alt="User photo" class="img-fluid rounded-circle border border-info shadow-sm" style="max-width: 200px;">
                </div>
            </div>
        </div>

        <#if chatHistory??>
            <div class="card mb-4">
                <div class="card-body">
                    <h3 class="card-title text-success"><i class="fas fa-history"></i> Chat History</h3>
                    <ul class="list-group">
                        <#list chatHistory as message>
                            <li class="list-group-item"><i class="fas fa-comment-dots text-primary"></i> ${message}</li>
                        </#list>
                    </ul>
                </div>
            </div>
        </#if>

        <div class="card">
            <div class="card-body">
                <h3 class="card-title text-warning"><i class="fas fa-paper-plane"></i> Send a Message</h3>
                <form action="messages" method="POST">
                    <div class="mb-3">
                        <textarea name="message" rows="4" class="form-control shadow-sm" placeholder="Write your message here..." required></textarea>
                    </div>
                    <input type="hidden" name="id" value="${user.id}">
                    <button type="submit" class="btn btn-primary w-100"><i class="fas fa-paper-plane"></i> Send Message</button>
                </form>
            </div>
        </div>
    <#else>
        <div class="alert alert-warning text-center mt-4" role="alert">
            <i class="fas fa-exclamation-triangle"></i> No user selected for chat. Please return to the main page and select a user.
        </div>
    </#if>

    <div class="text-center mt-4">
        <form action="home" method="GET" style="display: inline;">
            <button type="submit" class="btn btn-secondary"><i class="fas fa-arrow-left"></i> Back</button>
        </form>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>



