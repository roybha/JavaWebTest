<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>User Info</title>
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
    .btn-primary:hover {
      background-color: #0056b3;
    }
    .img-container {
      max-width: 200px;
      margin: auto;
      border-radius: 12px;
      overflow: hidden;
    }
    img {
      width: 100%;
      border-radius: 12px;
    }
  </style>
</head>
<body>

<div class="container py-4">
  <h1 class="text-center mb-4 text-primary"><i class="fas fa-user"></i> User Information</h1>

  <div class="card mb-4">
    <div class="card-body">
      <h3 class="card-title text-info"><i class="fas fa-info-circle"></i> About User</h3>
      <p><strong>Id:</strong> ${id}</p>
      <p><strong>Name:</strong> ${name}</p>
      <p><strong>Age:</strong> ${age}</p>
      <div class="text-center img-container">
        <img src="${file}" alt="User Image" class="shadow-sm">
      </div>
    </div>
  </div>

  <div class="card mb-4">
    <div class="card-body">
      <h3 class="card-title text-success"><i class="fas fa-check-circle"></i> Choose an Action</h3>
      <div class="d-flex justify-content-around">
        <!-- Форма для Yes -->
        <form action="answer/yes" method="POST">
          <input type="hidden" name="id" value="${id}">
          <input type="hidden" name="name" value="${name}">
          <input type="hidden" name="age" value="${age}">
          <input type="hidden" name="file" value="${file}">
          <input type="hidden" name="gender" value="${gender}">
          <button type="submit" class="btn btn-success"><i class="fas fa-thumbs-up"></i> Yes</button>
        </form>

        <!-- Форма для No -->
        <form action="answer/no" method="POST">
          <input type="hidden" name="id" value="${id}">
          <input type="hidden" name="name" value="${name}">
          <input type="hidden" name="age" value="${age}">
          <input type="hidden" name="file" value="${file}">
          <input type="hidden" name="gender" value="${gender}">
          <button type="submit" class="btn btn-danger"><i class="fas fa-thumbs-down"></i> No</button>
        </form>
      </div>
    </div>
  </div>

  <div class="card mb-4">
    <div class="card-body text-center">
      <form action="answer" method="GET" style="display:inline;">
        <button type="submit" class="btn btn-warning"><i class="fas fa-history"></i> Choices History</button>
      </form>
      <form action="liked" method="GET" style="display:inline;">
        <button type="submit" class="btn btn-info"><i class="fas fa-heart"></i> Liked Users</button>
      </form>
      <form action="home" method="GET" style="display:inline;">
        <button type="submit" class="btn btn-secondary"><i class="fas fa-arrow-left"></i> Back</button>
      </form>
    </div>
  </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
