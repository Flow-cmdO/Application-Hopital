<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Patient" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Patients — Hôpital</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body class="bg-light">
<nav class="navbar navbar-dark bg-primary navbar-expand-lg">
    <div class="container">
        <a class="navbar-brand fw-bold" href="index.jsp"><i class="bi bi-hospital me-2"></i>Hôpital</a>
        <div class="navbar-nav ms-auto">
            <a class="nav-link text-white fw-bold" href="patients">Patients</a>
            <a class="nav-link text-white" href="medecins">Médecins</a>
            <a class="nav-link text-white" href="soins">Soins</a>
            <a class="nav-link text-white" href="salles">Salles</a>
        </div>
    </div>
</nav>
<div class="container my-4">
    <h2 class="fw-bold mb-4"><i class="bi bi-people-fill text-primary me-2"></i>Gestion des Patients</h2>
    <% String msg = (String) session.getAttribute("message");
       String msgType = (String) session.getAttribute("messageType");
       if (msg != null) { %>
    <div class="alert alert-<%= msgType != null ? msgType : "info" %> alert-dismissible" role="alert">
        <%= msg %>
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
    <% session.removeAttribute("message"); session.removeAttribute("messageType"); } %>
    <div class="row g-3 mb-4">
        <div class="col-md-4">
            <div class="card text-white bg-primary text-center p-3">
                <h6>Total patients</h6>
                <h3 class="fw-bold"><%= request.getAttribute("totalPatients") %></h3>
            </div>
        </div>
    </div>
    <div class="card mb-4">
        <div class="card-body">
            <form method="get" action="patients" class="row g-2 align-items-end">
                <div class="col-md-3">
                    <label class="form-label">ID</label>
                    <input type="text" name="id" class="form-control" value="<%= request.getAttribute("id") != null ? request.getAttribute("id") : "" %>">
                </div>
                <div class="col-md-3">
                    <label class="form-label">Nom</label>
                    <input type="text" name="nom" class="form-control" value="<%= request.getAttribute("nom") != null ? request.getAttribute("nom") : "" %>">
                </div>
                <div class="col-md-3">
                    <label class="form-label">Prénom</label>
                    <input type="text" name="prenom" class="form-control" value="<%= request.getAttribute("prenom") != null ? request.getAttribute("prenom") : "" %>">
                </div>
                <div class="col-md-3">
                    <button type="submit" class="btn btn-primary w-100"><i class="bi bi-search me-1"></i>Filtrer</button>
                </div>
            </form>
        </div>
    </div>
    <div class="card mb-4">
        <div class="card-body p-0">
            <table class="table table-hover table-striped mb-0">
                <thead class="table-primary">
                    <tr><th>ID</th><th>Nom</th><th>Prénom</th><th>Date de naissance</th><th>Actions</th></tr>
                </thead>
                <tbody>
                <% List<Patient> patients = (List<Patient>) request.getAttribute("patients");
                   if (patients != null && !patients.isEmpty()) {
                       for (Patient p : patients) { %>
                <tr>
                    <td><%= p.getId() %></td>
                    <td><%= p.getNom() %></td>
                    <td><%= p.getPrenom() %></td>
                    <td><%= p.getDateNaissance() != null ? p.getDateNaissance() : "-" %></td>
                    <td>
                        <button class="btn btn-sm btn-warning me-1" onclick="ouvrirModif('<%= p.getId() %>', '<%= p.getNom() %>', '<%= p.getPrenom() %>')"><i class="bi bi-pencil"></i></button>
                        <a href="patients?action=supprimer&id=<%= p.getId() %>" class="btn btn-sm btn-danger" onclick="return confirm('Supprimer ce patient ?')"><i class="bi bi-trash"></i></a>
                    </td>
                </tr>
                <% } } else { %>
                <tr><td colspan="5" class="text-center text-muted">Aucun patient trouvé.</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
    <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalAjouter"><i class="bi bi-plus-circle me-1"></i>Ajouter un patient</button>
</div>
<div class="modal fade" id="modalAjouter" tabindex="-1">
    <div class="modal-dialog"><div class="modal-content">
        <div class="modal-header bg-primary text-white"><h5 class="modal-title">Ajouter un patient</h5><button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button></div>
        <form method="post" action="patients">
            <div class="modal-body">
                <input type="hidden" name="action" value="ajouter">
                <div class="mb-3"><label class="form-label">ID *</label><input type="text" name="id" class="form-control" required></div>
                <div class="mb-3"><label class="form-label">Nom *</label><input type="text" name="nom" class="form-control" required></div>
                <div class="mb-3"><label class="form-label">Prénom *</label><input type="text" name="prenom" class="form-control" required></div>
                <div class="mb-3"><label class="form-label">Date de naissance</label><input type="date" name="dateNaissance" class="form-control"></div>
            </div>
            <div class="modal-footer"><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button><button type="submit" class="btn btn-primary">Ajouter</button></div>
        </form>
    </div></div>
</div>
<div class="modal fade" id="modalModifier" tabindex="-1">
    <div class="modal-dialog"><div class="modal-content">
        <div class="modal-header bg-warning"><h5 class="modal-title">Modifier un patient</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div>
        <form method="post" action="patients">
            <div class="modal-body">
                <input type="hidden" name="action" value="modifier">
                <input type="hidden" name="id" id="modifId">
                <div class="mb-3"><label class="form-label">Nom *</label><input type="text" name="nom" id="modifNom" class="form-control" required></div>
                <div class="mb-3"><label class="form-label">Prénom *</label><input type="text" name="prenom" id="modifPrenom" class="form-control" required></div>
            </div>
            <div class="modal-footer"><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button><button type="submit" class="btn btn-warning">Modifier</button></div>
        </form>
    </div></div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
function ouvrirModif(id, nom, prenom) {
    document.getElementById('modifId').value = id;
    document.getElementById('modifNom').value = nom;
    document.getElementById('modifPrenom').value = prenom;
    new bootstrap.Modal(document.getElementById('modalModifier')).show();
}
</script>
</body></html>