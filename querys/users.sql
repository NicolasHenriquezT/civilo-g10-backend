INSERT INTO users (dtype, userid, name, surname, email, password, phone_number, commune, birth_date, age, company_name, request, role, disponibility) 
VALUES
('UserEntity', 1, 'Marcelo', 'Civilo', 'mcivilo@gmail.com', 'admin', '0 1234 5678', 
    (SELECT commune FROM civilo_roller_db.coverages WHERE commune = 'Santiago'), '1990-07-25', 40, null, null,
        (SELECT roleID FROM civilo_roller_db.roles WHERE account_type = 'Administrador'),null),
('UserEntity', 2, 'Ejecutivo', '1', 'ejecutivo@gmail.com', 'ejecutivo', '0 1234 5678',
    (SELECT commune FROM civilo_roller_db.coverages WHERE commune = 'Vallenar'), '1999-12-10', 24, null, null,
        (SELECT roleID FROM civilo_roller_db.roles WHERE account_type = 'Ejecutivo'),null),
('SellerEntity', 3, 'Vendedor', '1', 'vendedor@gmail.com', 'vendedor', '0 1234 5678',
    (SELECT commune FROM civilo_roller_db.coverages WHERE commune = 'Petorca'), '2000-04-10', 23, "Compañia 1", null,
        (SELECT roleID FROM civilo_roller_db.roles WHERE account_type = 'Vendedor'),1),
('UserEntity', 4, 'Cliente', '1', 'cliente@gmail.com', 'cliente', '0 1234 5678',
    (SELECT commune FROM civilo_roller_db.coverages WHERE commune = 'Hualpén'), '2002-01-26', 21, null, null,
        (SELECT roleID FROM civilo_roller_db.roles WHERE account_type = 'Cliente'),null);