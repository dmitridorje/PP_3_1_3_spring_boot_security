let fillMyTable = document.getElementById('fillMyTable');
let user_roles = document.getElementById('userRoles');
let selector = document.getElementById('selector');

showAll();

function showAll() {
    fetch("/api/admin/roles")
        .then(resp => resp.json())
        .then(roles => {
            let appendix = ``;
            let node_id;
            let node_href;
            let node_val;
            let h_light;
            let a_node_id
            for (let i = 0; i < roles.length; i++) {
                if (roles[i].name.indexOf("ADMIN") !== -1) {
                    node_id = 'id="admin"';
                    node_href = 'href="/admin/users"';
                    node_val = "Admin";
                    h_light = "active";
                    a_node_id = 'id="adminClick"';
                } else {
                    node_id = '';
                    node_href = 'href="/user"';
                    node_val = "User";
                    h_light = "";
                    a_node_id = "";
                }
                appendix += `<li ${node_id} class="nav-item">
                            <a ${a_node_id} class="nav-link ${h_light}" ${node_href}>${node_val}</a>
                        </li>`;
            }
            selector.innerHTML = appendix;

            fetch("/api/admin/users")
                .then(response => response.json())
                .then(users => {
                    let tr;
                    for (let i = 0; i < users.length; i++) {
                        tr = document.createElement('tr');
                        tr.setAttribute("id", `user${users[i].id}`)
                        tr.innerHTML = `
                            <td>${users[i].id}</td>
                            <td>${users[i].username}</td>
                            <td>${users[i].age}</td>
                            <td>${users[i].name}</td>
                            <td>${users[i].surname}</td>
                            <td>${users[i].email}</td>
                            <td>${users[i].roles.map(role => " " + role.name)}</td>
                            <td><button class="btn btn-info" data-toggle="modal" data-target="#editModal" onclick="getUserFieldsForEditModal(${users[i].id})">Edit</button></td>
                            <td><button class="btn btn-danger" data-toggle="modal" data-target="#deleteModal" onclick="getUserFieldsForDelModal(${users[i].id})">Delete</button></td>
                        `;
                        fillMyTable.append(tr);
                    }
                });
        });


    fetch("http://localhost:8080/api/admin/user")
        .then((response) => {
            return response.json();
        })
        .then(user => {
            let rls = `${user.roles.map(role => " " + role.name)}`;
            user_roles.innerHTML = `<strong>${user.email}</strong> with roles: <strong>${rls}</strong>`;
        });
}


let editUserForm = document.getElementById("editUserForm");

function getUserFieldsForEditModal(id) {
    fetch(`/api/admin/${id}`)
        .then(response => response.json())
        .then(user => {
            editUserForm.id.value = user.id;
            editUserForm.username.value = user.username;
            editUserForm.age.value = user.age;
            editUserForm.name.value = user.name;
            editUserForm.surname.value = user.surname;
            editUserForm.password.value = user.password;
            editUserForm.email.value = user.email;
            setOptionRoles("rolesEdit", true, user.roles);
        });
}

editUserForm.addEventListener('submit', editUserListener => {
    editUserListener.preventDefault();
    let editUserRoles = [];
    let markedRoles = editUserForm.roles.selectedOptions;
    for (let i = 0; i < markedRoles.length; i++) {
        editUserRoles.push({name: markedRoles[i].textContent});
    }
    let bodyInfo = JSON.stringify({
        id: editUserForm.id.value,
        username: editUserForm.username.value,
        age: editUserForm.age.value,
        name: editUserForm.name.value,
        surname: editUserForm.surname.value,
        password: editUserForm.password.value,
        email: editUserForm.email.value,
        roles: editUserRoles
    });
    let reqToEdit = {
        method: "PATCH",
        headers: {"Content-Type": "application/json"},
        body: bodyInfo
    }
    fetch(`/api/admin`, reqToEdit)
        .then(response => response.json())
        .then(updatedUser => {
            let editUserRow = document.getElementById(`user${updatedUser.id}`);
            editUserRow.innerHTML = `
                <td>${updatedUser.id}</td>
                <td>${updatedUser.username}</td>
                <td>${updatedUser.age}</td>
                <td>${updatedUser.name}</td>
                <td>${updatedUser.surname}</td>
                <td>${updatedUser.email}</td>
                <td>${updatedUser.roles.map(role => " " + role.name)}</td>
                <td><button class="btn btn-info" data-toggle="modal" data-target="#editModal" onclick="getUserFieldsForEditModal(${updatedUser.id})">Edit</button></td>
                <td><button class="btn btn-danger" data-toggle="modal" data-target="#deleteModal" onclick="getUserFieldsForDelModal(${updatedUser.id})">Delete</button></td>
            `;
            document.getElementById("editModalClose").click();
        });
});


let deleteUserForm = document.getElementById("deleteUserForm")

function getUserFieldsForDelModal(id) {
    fetch(`/api/admin/${id}`)
        .then(response => response.json())
        .then(user => {
            deleteUserForm.id1.value = user.id;
            deleteUserForm.username1.value = user.username;
            deleteUserForm.age1.value = user.age;
            deleteUserForm.name1.value = user.name;
            deleteUserForm.surname1.value = user.surname;
            deleteUserForm.email1.value = user.email;
            setOptionRoles("rolesDelete", true, user.roles);
        });
}

deleteUserForm.addEventListener('submit', delUserListener => {
    delUserListener.preventDefault();
    let reqToDelete = {
        method: "DELETE",
        headers: {"Content-Type": "application/json"},
        body: deleteUserForm.id1.value,
    };
    fetch(`/api/admin`, reqToDelete)
        .then(response => response.json())
        .then(userToDelId => {
            let rowToRemove = document.getElementById(`user${userToDelId}`);
            rowToRemove.remove();
            document.getElementById("deleteModalClose").click();
        });
});


let addUserForm = document.getElementById("addNewUser")

fetch("/api/admin/roles")
    .then(resp => resp.json())
    .then(roles => {
        let selectEdit = document.getElementById("rolesNew");
        let appendix = ``;
        for (let i = 0; i < roles.length; i++) {
            appendix += `<option value="${i + 1}">${roles[i].name}</option>`
        }
        selectEdit.innerHTML = appendix;
    });

addUserForm.addEventListener("submit", newUserEventListener => {
    newUserEventListener.preventDefault();
    let addUserRoles = [];
    let markedRoles = addUserForm.rolesNew.selectedOptions;
    for (let i = 0; i < markedRoles.length; i++) {
        addUserRoles.push({name: markedRoles[i].textContent});
    }
    let bodyInfo = JSON.stringify({
        username: addUserForm.usernameNew.value,
        age: addUserForm.ageNew.value,
        name: addUserForm.nameNew.value,
        surname: addUserForm.surnameNew.value,
        password: addUserForm.passwordNew.value,
        email: addUserForm.emailNew.value,
        roles: addUserRoles
    });
    let reqToAdd = {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: bodyInfo
    };
    fetch("/api/admin", reqToAdd)
        .then(resp => resp.json())
        .then(newUser => {
            let tr = document.createElement('tr');
            tr.setAttribute("id", `user${newUser.id}`)
            tr.innerHTML = `
                    <td>${newUser.id}</td>
                    <td>${newUser.username}</td>
                    <td>${newUser.age}</td>
                    <td>${newUser.name}</td>
                    <td>${newUser.surname}</td>
                    <td>${newUser.email}</td>
                    <td>${newUser.roles.map(role => " " + role.name)}</td>
                    <td><button class="btn btn-info" data-toggle="modal" data-target="#editModal" onclick="getUserFieldsForEditModal(${newUser.id})">Edit</button></td>
                    <td><button class="btn btn-danger" data-toggle="modal" data-target="#deleteModal" onclick="getUserFieldsForDelModal(${newUser.id})">Delete</button></td>
                `;
            fillMyTable.append(tr);
            document.getElementById("adminClick").click();
        });

});

function setOptionRoles(selectIdToAttach, highlights = false, curUserRoles = []) {
    fetch("/api/admin/roles")
        .then(resp => resp.json())
        .then(roles => {
            let selectEdit = document.getElementById(selectIdToAttach);
            let appendix = ``;
            let checkRoles;
            let hLight = "";
            for (let i = 0; i < roles.length; i++) {
                if (highlights) {
                    checkRoles = curUserRoles.map(r => r.name + "/").toString().indexOf(roles[i].name) !== -1;
                    hLight = (checkRoles) ? "selected" : "";
                }
                appendix += `<option value="${i + 1}" ${hLight}>${roles[i].name}</option>`;
            }
            selectEdit.innerHTML = appendix;
        });
}