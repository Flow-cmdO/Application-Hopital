<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Salle" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Salles — Hôpital</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body class="bg-light">
<nav class="navbar navbar-dark bg-warning navbar-expand-lg">
    <div class="container">
        <a class="navbar-brand fw-bold text-dark" href="index.jsp"><i class="bi bi-hospital me-2"></i>Hôpital</a>
        <div class="navbar-nav ms-auto">
            <a class="nav-link text-dark" href="patients">Patients</a>
            <a class="nav-link text-dark" href="medecins">Médecins</a>
            <a class="nav-link text-dark" href="soins">Soins</a>
            <a class="nav-link text-dark fw-bold" href="salles">Salles</a>
        </div>
    </div>
</nav>
<div class="container my-4">
    <h2 class="fw-bold mb-4"><i class="bi bi-building-fill text-warning me-2"></i>Gestion des Salles</h2>
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
            <div class="card text-dark bg-warning text-center p-3">
                <h6>Total salles</h6>
                <h3 class="fw-bold"><%= request.getAttribute("totalSalles") %></h3>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card text-white bg-success text-center p-3">
                <h6>Salles disponibles</h6>
                <h3 class="fw-bold"><%= request.getAttribute("sallesDisponibles") %></h3>
            </div>
        </div>
    </div>
    <div class="card mb-4">
        <div class="card-body">
            <form method="get" action="salles" class="row g-2 align-items-end">
                <div class="col-md-3">
                    <label class="form-label">Numéro</label>
                    <input type="text" name="numero" class="form-control" value="<%= request.getAttribute("numero") != null ? request.getAttribute("numero") : "" %>">
                </div>
                <div class="col-md-3">
                    <label class="form-label">Type</label>
                    <input type="text" name="type" class="form-control" placeholder="Chirurgie, Urgences..." value="<%= request.getAttribute("type") != null ? request.getAttribute("type") : "" %>">
                </div>
                <div class="col-md-3">
                    <label class="form-label">Disponibilité</label>
                    <select name="disponible" class="form-select">
                        <option value="">Toutes</option>
                        <option value="true" <%= "true".equals(request.getAttribute("disponible")) ? "selected" : "" %>>Disponibles</option>
                    </select>
                </div>
                <div class="col-md-3">
                    <button type="submit" class="btn btn-warning w-100"><i class="bi bi-search me-1"></i>Filtrer</button>
                </div>
            </form>
        </div>
    </div>
    <div class="card mb-4">
        <div class="card-body p-0">
            <table class="table table-hover table-striped mb-0">
                <thead class="table-warning">
                    <tr><th>Numéro</th><th>Type</th><th>Nb lits</th><th>Disponible</th><th>Actions</th></tr>
                </thead>
                <tbody>
                <% List<Salle> salles = (List<Salle>) request.getAttribute("salles");
                   if (salles != null && !salles.isEmpty()) {
                       for (Salle s : salles) { %>
                <tr>
                    <td><%= s.getNumero() %></td>
                    <td><%= s.getType() %></td>
                    <td><%= s.getLits().size() %></td>
                    <td><% if (s.isDisponible()) { %><span class="badge bg-success">Disponible</span><% } else { %><span class="badge bg-danger">Occupée</span><% } %></td>
                    <td>
                        <button class="btn btn-sm btn-warning me-1" onclick="ouvrirModif('<%= s.getNumero() %>', '<%= s.getType() %>')"><i class="bi bi-pencil"></i></button>
                        <a href="salles?action=supprimer&numero=<%= s.getNumero() %>" class="btn btn-sm btn-danger" onclick="return confirm('Supprimer cette salle ?')"><i class="bi bi-trash"></i></a>
                    </td>
                </tr>
                <% } } else { %>
                <tr><td colspan="5" class="text-center text-muted">Aucune salle trouvée.</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
    <button class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#modalAjouter"><i class="bi bi-plus-circle me-1"></i>Ajouter une salle</button>
</div>
<div class="modal fade" id="modalAjouter" tabindex="-1">
    <div class="modal-dialog"><div class="modal-content">
        <div class="modal-header bg-warning"><h5 class="modal-title">Ajouter une salle</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div>
        <form method="post" action="salles">
            <div class="modal-body">
                <input type="hidden" name="action" value="ajouter">
                <div class="mb-3"><label class="form-label">Numéro *</label><input type="text" name="numero" class="form-control" required></div>
                <div class="mb-3">
                    <label class="form-label">Type *</label>
                    <select name="type" class="form-select" required>
                        <option value="">-- Choisir --</option>
                        <option value="Chirurgie">Chirurgie</option>
                        <option value="Urgences">Urgences</option>
                        <option value="Réanimation">Réanimation</option>
                        <option value="Pédiatrie">Pédiatrie</option>
                        <option value="Cardiologie">Cardiologie</option>
                    </select>
                </div>
            </div>
            <div class="modal-footer"><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button><button type="submit" class="btn btn-warning">Ajouter</button></div>
        </form>
    </div></div>
</div>
<div class="modal fade" id="modalModifier" tabindex="-1">
    <div class="modal-dialog"><div class="modal-content">
        <div class="modal-header bg-warning"><h5 class="modal-title">Modifier une salle</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div>
        <form method="post" action="salles">
            <div class="modal-body">
                <input type="hidden" name="action" value="modifier">
                <input type="hidden" name="numero" id="modifNumero">
                <div class="mb-3">
                    <label class="form-label">Type *</label>
                    <select name="type" id="modifType" class="form-select" required>
                        <option value="Chirurgie">Chirurgie</option>
                        <option value="Urgences">Urgences</option>
                        <option value="Réanimation">Réanimation</option>
                        <option value="Pédiatrie">Pédiatrie</option>
                        <option value="Cardiologie">Cardiologie</option>
                    </select>
                </div>
            </div>
            <div class="modal-footer"><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button><button type="submit" class="btn btn-warning">Modifier</button></div>
        </form>
    </div></div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
function ouvrirModif(numero, type) {
    document.getElementById('modifNumero').value = numero;
    document.getElementById('modifType').value = type;
    new bootstrap.Modal(document.getElementById('modalModifier')).show();
}
</script>
</body></html>