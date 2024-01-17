let fillMyTable = document.getElementById('fillMyTable');
let user_roles = document.getElementById('user_roles');
let selector = document.getElementById('selector');

fetch("/api/user/roles")
    .then(resp => resp.json())
    .then(roles => {
        let appendix = ``;
        let node_id;
        let node_href;
        let node_val;
        let h_light;
        for (let i = 0; i < roles.length; i++) {
            if (roles[i].name.indexOf("ADMIN")!==-1) {
                node_id = 'id="admin"';
                node_href = 'href="/admin/users"';
                node_val = "Admin";
                h_light = "";
            } else {
                node_id = '';
                node_href = 'href="/user"';
                node_val = "User";
                h_light = "active";
            }
            appendix += `<li ${node_id} class="nav-item">
                            <a class="nav-link ${h_light}" ${node_href}>${node_val}</a>
                        </li>`;
        }
        selector.innerHTML = appendix;

        fetch("http://localhost:8080/api/user")
            .then((response) => {
                return response.json();
            })
            .then(user => {
                let rls = `${user.roles.map(role => " " + role.name)}`;
                let row = `<tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.age}</td>
                        <td>${user.name}</td>
                        <td>${user.surname}</td>
                        <td>${user.email}</td>
                        <td>${rls}</td>
                   </tr>`;
                fillMyTable.innerHTML = row;
                user_roles.innerHTML = `<strong>${user.email}</strong> with roles: <strong>${rls}</strong>`;
                if (rls.indexOf("ADMIN") === -1) {
                    document.getElementById('admin').style.display = "none";
                }
            });
    });