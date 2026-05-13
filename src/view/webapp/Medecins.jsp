<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Medecin" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Médecins — Hôpital</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body class="bg-light">
<nav class="navbar navbar-dark bg-success navbar-expand-lg">
    <div class="container">
        <a class="navbar-brand fw-bold" href="index.jsp"><i class="bi bi-hospital me-2"></i>Hôpital</a>
        <div class="navbar-nav ms-auto">
            <a class="nav-link text-white" href="patients">Patients</a>
            <a class="nav-link text-white fw-bold" href="medecins">Médecins</a>
            <a class="nav-link text-white" href="soins">Soins</a>
            <a class="nav-link text-white" href="salles">Salles</a>
        </div>
    </div>
</nav>
<div class="container my-4">
    <h2 class="fw-bold mb-4"><i class="bi bi-person-badge-fill text-success me-2"></i>Gestion des Médecins</h2>
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
            <div class="card text-white bg-success text-center p-3">
                <h6>Total médecins</h6>
                <h3 class="fw-bold"><%= request.getAttribute("totalMedecins") %></h3>
            </div>
        </div>
    </div>
    <div class="card mb-4">
        <div class="card-body">
            <form method="get" action="medecins" class="row g-2 align-items-end">
                <div class="col-md-4">
                    <label class="form-label">Nom</label>
                    <input type="text" name="nom" class="form-control" value="<%= request.getAttribute("nom") != null ? request.getAttribute("nom") : "" %>">
                </div>
                <div class="col-md-4">
                    <label class="form-label">Spécialité</label>
                    <input type="text" name="specialite" class="form-control" value="<%= request.getAttribute("specialite") != null ? request.getAttribute("specialite") : "" %>">
                </div>
                <div class="col-md-4">
                    <button type="submit" class="btn btn-success w-100"><i class="bi bi-search me-1"></i>Filtrer</button>
                </div>
            </form>
        </div>
    </div>
    <div class="card mb-4">
        <div class="card-body p-0">
            <table class="table table-hover table-striped mb-0">
                <thead class="table-success">
                    <tr><th>ID</th><th>Nom</th><th>Prénom</th><th>Spécialité</th><th>Actions</th></tr>
                </thead>
                <tbody>
                <% List<Medecin> medecins = (List<Medecin>) request.getAttribute("medecins");
                   if (medecins != null && !medecins.isEmpty()) {
                       for (Medecin m : medecins) { %>
                <tr>
                    <td><%= m.getId() %></td>
                    <td><%= m.getNom() %></td>
                    <td><%= m.getPrenom() %></td>
                    <td><%= m.getSpecialite() %></td>
                    <td>
                        <button class="btn btn-sm btn-warning me-1" onclick="ouvrirModif('<%= m.getId() %>', '<%= m.getSpecialite() %>')"><i class="bi bi-pencil"></i></button>
                        <a href="medecins?action=supprimer&id=<%= m.getId() %>" class="btn btn-sm btn-danger" onclick="return confirm('Supprimer ce médecin ?')"><i class="bi bi-trash"></i></a>
                    </td>
                </tr>
                <% } } else { %>
                <tr><td colspan="5" class="text-center text-muted">Aucun médecin trouvé.</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
    <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#modalAjouter"><i class="bi bi-plus-circle me-1"></i>Ajouter un médecin</button>
</div>
<div class="modal fade" id="modalAjouter" tabindex="-1">
    <div class="modal-dialog"><div class="modal-content">
        <div class="modal-header bg-success text-white"><h5 class="modal-title">Ajouter un médecin</h5><button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button></div>
        <form method="post" action="medecins">
            <div class="modal-body">
                <input type="hidden" name="action" value="ajouter">
                <div class="mb-3"><label class="form-label">ID *</label><input type="text" name="id" class="form-control" required></div>
                <div class="mb-3"><label class="form-label">Nom *</label><input type="text" name="nom" class="form-control" required></div>
                <div class="mb-3"><label class="form-label">Prénom *</label><input type="text" name="prenom" class="form-control" required></div>
                <div class="mb-3"><label class="form-label">Spécialité *</label><input type="text" name="specialite" class="form-control" required></div>
            </div>
            <div class="modal-footer"><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button><button type="submit" class="btn btn-success">Ajouter</button></div>
        </form>
    </div></div>
</div>
<div class="modal fade" id="modalModifier" tabindex="-1">
    <div class="modal-dialog"><div class="modal-content">
        <div class="modal-header bg-warning"><h5 class="modal-title">Modifier un médecin</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div>
        <form method="post" action="medecins">
            <div class="modal-body">
                <input type="hidden" name="action" value="modifier">
                <input type="hidden" name="id" id="modifId">
                <div class="mb-3"><label class="form-label">Spécialité *</label><input type="text" name="specialite" id="modifSpec" class="form-control" required></div>
            </div>
            <div class="modal-footer"><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button><button type="submit" class="btn btn-warning">Modifier</button></div>
        </form>
    </div></div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
function ouvrirModif(id, spec) {
    document.getElementById('modifId').value = id;
    document.getElementById('modifSpec').value = spec;
    new bootstrap.Modal(document.getElementById('modalModifier')).show();
}
</script>
</body></html>