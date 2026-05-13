<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tableau de bord — Hôpital</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        body { background-color: #f0f4f8; }
        .navbar { background-color: #0d6efd; }
        .stat-card { border-left: 4px solid; border-radius: 8px; }
        .stat-card.blue  { border-left-color: #0d6efd; }
        .stat-card.green { border-left-color: #198754; }
        .stat-card.red   { border-left-color: #dc3545; }
        .stat-card.yellow{ border-left-color: #ffc107; }
    </style>
</head>
<body>

<nav class="navbar navbar-dark navbar-expand-lg">
    <div class="container">
        <a class="navbar-brand fw-bold" href="index.jsp"><i class="bi bi-hospital me-2"></i>Gestionnaire Hôpital</a>
        <div class="navbar-nav ms-auto">
            <a class="nav-link text-white fw-bold" href="index.jsp">Tableau de bord</a>
            <a class="nav-link text-white" href="patients">Patients</a>
            <a class="nav-link text-white" href="medecins">Médecins</a>
            <a class="nav-link text-white" href="soins">Soins</a>
            <a class="nav-link text-white" href="salles">Salles</a>
        </div>
    </div>
</nav>

<div style="background: linear-gradient(135deg, #0d6efd, #0dcaf0); color:white; padding:40px 0;" class="text-center">
    <h1 class="display-6 fw-bold"><i class="bi bi-graph-up me-2"></i>Tableau de bord</h1>
    <p class="lead">Vue d'ensemble de l'activité hospitalière</p>
</div>

<div class="container my-4">

    <% String msg = (String) session.getAttribute("message");
       String msgType = (String) session.getAttribute("messageType");
       if (msg != null) { %>
    <div class="alert alert-<%= msgType != null ? msgType : "info" %> alert-dismissible" role="alert">
        <%= msg %>
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
    <% session.removeAttribute("message"); session.removeAttribute("messageType"); } %>

    <h4 class="fw-bold mb-3">Statistiques globales</h4>
    <div class="row g-3 mb-5">
        <div class="col-md-3">
            <div class="card stat-card blue p-3">
                <div class="d-flex align-items-center gap-3">
                    <i class="bi bi-people-fill text-primary" style="font-size:2rem;"></i>
                    <div>
                        <p class="text-muted mb-0" style="font-size:13px;">Total patients</p>
                        <h3 class="fw-bold mb-0"><%= request.getAttribute("totalPatients") != null ? request.getAttribute("totalPatients") : 0 %></h3>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card stat-card green p-3">
                <div class="d-flex align-items-center gap-3">
                    <i class="bi bi-person-badge-fill text-success" style="font-size:2rem;"></i>
                    <div>
                        <p class="text-muted mb-0" style="font-size:13px;">Total médecins</p>
                        <h3 class="fw-bold mb-0"><%= request.getAttribute("totalMedecins") != null ? request.getAttribute("totalMedecins") : 0 %></h3>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card stat-card red p-3">
                <div class="d-flex align-items-center gap-3">
                    <i class="bi bi-clipboard2-pulse-fill text-danger" style="font-size:2rem;"></i>
                    <div>
                        <p class="text-muted mb-0" style="font-size:13px;">Total soins</p>
                        <h3 class="fw-bold mb-0"><%= request.getAttribute("totalSoins") != null ? request.getAttribute("totalSoins") : 0 %></h3>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card stat-card yellow p-3">
                <div class="d-flex align-items-center gap-3">
                    <i class="bi bi-building-fill text-warning" style="font-size:2rem;"></i>
                    <div>
                        <p class="text-muted mb-0" style="font-size:13px;">Salles disponibles</p>
                        <h3 class="fw-bold mb-0">
                            <%= request.getAttribute("sallesDisponibles") != null ? request.getAttribute("sallesDisponibles") : 0 %>
                            <small class="text-muted fw-normal" style="font-size:14px;">/ <%= request.getAttribute("totalSalles") != null ? request.getAttribute("totalSalles") : 0 %></small>
                        </h3>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row g-4 mb-5">
        <div class="col-md-6">
            <div class="card p-3">
                <h6 class="fw-bold mb-3">Répartition des entités</h6>
                <canvas id="chartRepartition" height="200"></canvas>
            </div>
        </div>
        <div class="col-md-6">
            <div class="card p-3">
                <h6 class="fw-bold mb-3">Occupation des salles</h6>
                <canvas id="chartSalles" height="200"></canvas>
            </div>
        </div>
    </div>

    <h4 class="fw-bold mb-3">Navigation rapide</h4>
    <div class="row g-3">
        <div class="col-md-3">
            <a href="patients" class="text-decoration-none">
                <div class="card text-center p-4 h-100">
                    <i class="bi bi-people-fill text-primary" style="font-size:2.5rem;"></i>
                    <h5 class="mt-2 fw-bold">Patients</h5>
                    <p class="text-muted small mb-0">Gérer les admissions et dossiers</p>
                </div>
            </a>
        </div>
        <div class="col-md-3">
            <a href="medecins" class="text-decoration-none">
                <div class="card text-center p-4 h-100">
                    <i class="bi bi-person-badge-fill text-success" style="font-size:2.5rem;"></i>
                    <h5 class="mt-2 fw-bold">Médecins</h5>
                    <p class="text-muted small mb-0">Gérer les spécialités et plannings</p>
                </div>
            </a>
        </div>
        <div class="col-md-3">
            <a href="soins" class="text-decoration-none">
                <div class="card text-center p-4 h-100">
                    <i class="bi bi-clipboard2-pulse-fill text-danger" style="font-size:2.5rem;"></i>
                    <h5 class="mt-2 fw-bold">Soins</h5>
                    <p class="text-muted small mb-0">Consultations et actes médicaux</p>
                </div>
            </a>
        </div>
        <div class="col-md-3">
            <a href="salles" class="text-decoration-none">
                <div class="card text-center p-4 h-100">
                    <i class="bi bi-building-fill text-warning" style="font-size:2.5rem;"></i>
                    <h5 class="mt-2 fw-bold">Salles</h5>
                    <p class="text-muted small mb-0">Occupation et disponibilités</p>
                </div>
            </a>
        </div>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    const totalPatients     = <%= request.getAttribute("totalPatients")     != null ? request.getAttribute("totalPatients")     : 0 %>;
    const totalMedecins     = <%= request.getAttribute("totalMedecins")     != null ? request.getAttribute("totalMedecins")     : 0 %>;
    const totalSoins        = <%= request.getAttribute("totalSoins")        != null ? request.getAttribute("totalSoins")        : 0 %>;
    const totalSalles       = <%= request.getAttribute("totalSalles")       != null ? request.getAttribute("totalSalles")       : 0 %>;
    const sallesDisponibles = <%= request.getAttribute("sallesDisponibles") != null ? request.getAttribute("sallesDisponibles") : 0 %>;

    new Chart(document.getElementById('chartRepartition'), {
        type: 'doughnut',
        data: {
            labels: ['Patients', 'Médecins', 'Soins'],
            datasets: [{ data: [totalPatients, totalMedecins, totalSoins], backgroundColor: ['#0d6efd', '#198754', '#dc3545'] }]
        },
        options: { plugins: { legend: { position: 'bottom' } } }
    });

    new Chart(document.getElementById('chartSalles'), {
        type: 'bar',
        data: {
            labels: ['Disponibles', 'Occupées'],
            datasets: [{ label: 'Salles', data: [sallesDisponibles, totalSalles - sallesDisponibles], backgroundColor: ['#198754', '#dc3545'] }]
        },
        options: { plugins: { legend: { display: false } }, scales: { y: { beginAtZero: true, ticks: { stepSize: 1 } } } }
    });
</script>
</body>
</html>