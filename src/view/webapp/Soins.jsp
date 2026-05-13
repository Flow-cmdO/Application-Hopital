<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Consultation" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Soins — Hôpital</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body class="bg-light">
<nav class="navbar navbar-dark bg-danger navbar-expand-lg">
    <div class="container">
        <a class="navbar-brand fw-bold" href="index.jsp"><i class="bi bi-hospital me-2"></i>Hôpital</a>
        <div class="navbar-nav ms-auto">
            <a class="nav-link text-white" href="patients">Patients</a>
            <a class="nav-link text-white" href="medecins">Médecins</a>
            <a class="nav-link text-white fw-bold" href="soins">Soins</a>
            <a class="nav-link text-white" href="salles">Salles</a>
        </div>
    </div>
</nav>
<div class="container my-4">
    <h2 class="fw-bold mb-4"><i class="bi bi-clipboard2-pulse-fill text-danger me-2"></i>Gestion des Soins</h2>
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
            <div class="card text-white bg-danger text-center p-3">
                <h6>Total soins</h6>
                <h3 class="fw-bold"><%= request.getAttribute("totalSoins") %></h3>
            </div>
        </div>
    </div>
    <div class="card mb-4">
        <div class="card-body">
            <form method="get" action="soins" class="row g-2 align-items-end">
                <div class="col-md-3">
                    <label class="form-label">ID</label>
                    <input type="text" name="id" class="form-control" value="<%= request.getAttribute("id") != null ? request.getAttribute("id") : "" %>">
                </div>
                <div class="col-md-3">
                    <label class="form-label">Motif</label>
                    <input type="text" name="motif" class="form-control" value="<%= request.getAttribute("motif") != null ? request.getAttribute("motif") : "" %>">
                </div>
                <div class="col-md-3">
                    <label class="form-label">Date</label>
                    <input type="date" name="date" class="form-control" value="<%= request.getAttribute("date") != null ? request.getAttribute("date") : "" %>">
                </div>
                <div class="col-md-3">
                    <button type="submit" class="btn btn-danger w-100"><i class="bi bi-search me-1"></i>Filtrer</button>
                </div>
            </form>
        </div>
    </div>
    <div class="card mb-4">
        <div class="card-body p-0">
            <table class="table table-hover table-striped mb-0">
                <thead class="table-danger">
                    <tr><th>ID</th><th>Date</th><th>Motif</th><th>Description</th><th>Actions</th></tr>
                </thead>
                <tbody>
                <% List<Consultation> soins = (List<Consultation>) request.getAttribute("soins");
                   if (soins != null && !soins.isEmpty()) {
                       for (Consultation s : soins) { %>
                <tr>
                    <td><%= s.getId() %></td>
                    <td><%= s.getDate() != null ? s.getDate() : "-" %></td>
                    <td><%= s.getMotif() %></td>
                    <td><%= s.getDescription() != null ? s.getDescription() : "-" %></td>
                    <td>
                      <button class="btn btn-sm btn-warning me-1"
    onclick="ouvrirModif('<%= s.getId() %>')">
    <i class="bi bi-pencil"></i>
</button>

<a href="soins?action=supprimer&id=<%= s.getId() %>"
   class="btn btn-sm btn-danger"
   onclick="return confirm('Supprimer ce soin ?')">
    <i class="bi bi-trash"></i>
</a>
                    </td>
                </tr>
                <% } } else { %>
                <tr><td colspan="5" class="text-center text-muted">Aucun soin trouvé.</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
    <button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#modalAjouter"><i class="bi bi-plus-circle me-1"></i>Ajouter un soin</button>
</div>
<div class="modal fade" id="modalAjouter" tabindex="-1">
    <div class="modal-dialog"><div class="modal-content">
        <div class="modal-header bg-danger text-white"><h5 class="modal-title">Ajouter un soin</h5><button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button></div>
        <form method="post" action="soins">
            <div class="modal-body">
                <input type="hidden" name="action" value="ajouter">
                <div class="mb-3"><label class="form-label">ID *</label><input type="text" name="id" class="form-control" required></div>
                <div class="mb-3"><label class="form-label">Date</label><input type="date" name="date" class="form-control"></div>
                <div class="mb-3"><label class="form-label">Motif *</label><input type="text" name="motif" class="form-control" required></div>
                <div class="mb-3"><label class="form-label">Description</label><textarea name="description" class="form-control" rows="3"></textarea></div>
            </div>
            <div class="modal-footer"><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button><button type="submit" class="btn btn-danger">Ajouter</button></div>
        </form>
    </div></div>
</div>
<div class="modal fade" id="modalModifier" tabindex="-1">
    <div class="modal-dialog"><div class="modal-content">
        <div class="modal-header bg-warning"><h5 class="modal-title">Modifier un soin</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div>
        <form method="post" action="soins">
            <div class="modal-body">
                <input type="hidden" name="action" value="modifier">
                <input type="hidden" name="id" id="modifId">
                <div class="mb-3"><label class="form-label">Description</label><textarea name="description" id="modifDesc" class="form-control" rows="3"></textarea></div>
            </div>
            <div class="modal-footer"><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button><button type="submit" class="btn btn-warning">Modifier</button></div>
        </form>
    </div></div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
// function ouvrirModif(id, desc) {
//     document.getElementById('modifId').value = id;
//     document.getElementById('modifDesc').value = desc;
//     new bootstrap.Modal(document.getElementById('modalModifier')).show();
// }
function ouvrirModif(id) {
    document.getElementById('modifId').value = id;
    document.getElementById('modifDesc').value = '';
    new bootstrap.Modal(document.getElementById('modalModifier')).show();
}
</script>
</body></html>