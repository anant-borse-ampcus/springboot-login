package com.ampcus.login.specification;

import com.ampcus.login.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> hasEmailLike(String email) {
        return (root, query, cb) -> {
            if (email == null || email.trim().isEmpty()) return null;
            String pattern = "%" + email.trim().toLowerCase() + "%";
            return cb.like(cb.lower(root.get("email")), pattern);
        };
    }

    public static Specification<User> hasRole(User.Role role) {
        return (root, query, cb) -> {
            if (role == null) return null;
            return cb.equal(root.get("role"), role);
        };
    }

    public static Specification<User> hasEnabled(Boolean enabled) {
        return (root,query, cb) -> {
            if (enabled == null) return null;
            return cb.equal(root.get("enabled"), enabled);
        };
    }

    public static Specification<User> build(String email, User.Role role, Boolean enabled) {

        Specification<User> spec = (root, query, cb) -> cb.conjunction();

        if (email != null) spec = spec.and(hasEmailLike(email));
        if (role != null) spec = spec.and(hasRole(role));
        if (enabled != null) spec = spec.and(hasEnabled(enabled));

        return spec;
    }
}
//# 1) Add user (POST /api/admin/add)
//curl -i -X POST http://localhost:8080/api/admin/add \
//  -H "Content-Type: application/json" \
//  -d '{
//    "email": "user@example.com",
//    "password": "P@ssw0rd",
//    "role": "USER",
//    "enabled": true
//  }'
//
//# 2) Update user (PUT /api/admin/update/{userId})
//curl -i -X PUT http://localhost:8080/api/admin/update/1 \
//  -H "Content-Type: application/json" \
//  -d '{
//    "email": "updated@example.com",
//    "password": "NewP@ss",
//    "role": "ADMIN",
//    "enabled": true
//  }'
//
//# 3) Soft delete / deactivate user (PUT /api/admin/soft-delete/{userId})
//curl -i -X PUT http://localhost:8080/api/admin/soft-delete/1 \
//  -H "Content-Type: application/json"
//
//# 4) Permanently delete user (DELETE /api/admin/delete/{userId})
//curl -i -X DELETE http://localhost:8080/api/admin/delete/1
//
//# 5) Search users (GET /api/admin/search-users)
//# Example: search by partial email, role and enabled flag with paging
//curl -i -X GET "http://localhost:8080/api/admin/search-users?email=example&role=ADMIN&enabled=true&page=0&size=10" \
//  -H "Accept: application/json"
//
//# Notes:
//# - In Postman: create a request for each curl, set method, URL, Headers and raw JSON body where shown.
//# - If your API requires auth, add: -H "Authorization: Bearer <token>" to each request.
//# - Expected responses: 200 OK with message or paged JSON for search; 400 for validation/errors; 500 for server errors.