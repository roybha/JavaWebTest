<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>User Answers</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        table img {
            max-width: 100px;
            height: auto;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        }
    </style>
</head>
<body>

<div class="container py-4">
    <h1 class="text-center mb-4 text-primary"><i class="fas fa-table"></i> Users that you have answered</h1>

    <div class="table-responsive">
        <table class="table table-bordered table-hover align-middle">
            <thead class="table-primary text-center">
            <tr>
                <th>Answer</th>
                <th>User ID</th>
                <th>User Name</th>
                <th>User Age</th>
                <th>User Photo</th>
            </tr>
            </thead>
            <tbody>
            <#list answers as row>
                <tr>
                    <td class="text-center">
                        <#if row.answer??>
                            ${row.answer}
                        </#if>
                    </td>
                    <td class="text-center">${row.receiver_user.id}</td>
                    <td>${row.receiver_user.name}</td>
                    <td class="text-center">${row.receiver_user.age}</td>
                    <td class="text-center">
                        <img src="${row.receiver_user.file}" alt="User photo" class="img-fluid">
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <div class="text-center mt-4">
        <form action="users" method="GET">
            <button type="submit" class="btn btn-secondary"><i class="fas fa-arrow-left"></i> Back</button>
        </form>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
