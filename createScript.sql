create table Cliente
(
    id         bigserial    not null,
    idEmpresa  bigint       not null,
    bairro     varchar(255) not null,
    cnpj       varchar(255),
    cpf        varchar(255),
    logradouro varchar(255) not null,
    nome       varchar(255) not null,
    telefone   varchar(255) not null,
    primary key (id)
);
create table Empresa
(
    porcentagemComissaoEntregador float(53),
    id                            bigserial    not null,
    cnpj                          varchar(255) unique,
    cpf                           varchar(255) unique,
    nome                          varchar(255) not null,
    primary key (id)
);
create table Funcionario
(
    id        bigserial    not null,
    idEmpresa bigint       not null,
    nome      varchar(255) not null,
    senha     varchar(255) not null,
    telefone  varchar(255) not null unique,
    primary key (id)
);
create table ItemPedido
(
    quantidade    integer,
    valorUnitario float(53),
    id            bigserial not null,
    idPedido      bigint,
    idProduto     bigint,
    primary key (id)
);
create table Pedido
(
    data         date,
    valorTotal   float(53),
    id           bigserial not null,
    idCliente    bigint,
    idEntregador bigint,
    idEmpresa    bigint,
    observacoes  varchar(255),
    status       varchar(255) check (status in ('EM_PREPARACAO', 'SAIU_PARA_ENTREGA', 'ENTREGUE')),
    primary key (id)
);
create table Perfil
(
    id            bigserial   not null,
    idFuncionario bigint      not null,
    tipoPerfil    varchar(31) not null,
    primary key (id)
);
create table Produto
(
    empresa_id  bigint,
    id          bigserial    not null,
    codigo      varchar(255) not null,
    localizacao varchar(255),
    nome        varchar(255) not null,
    primary key (id)
);
alter table if exists Cliente
    add constraint FKpho1xugjo8nurag2egy0dsxg2 foreign key (idEmpresa) references Empresa;
alter table if exists Funcionario
    add constraint FKlup6mu3v6rc8nu4gda1gx2oay foreign key (idEmpresa) references Empresa;
alter table if exists ItemPedido
    add constraint FKijirvim8g8orndencmvk3eije foreign key (idPedido) references Pedido;
alter table if exists ItemPedido
    add constraint FKln7funvfobr6kvr0td9wo3sef foreign key (idProduto) references Produto;
alter table if exists Pedido
    add constraint FKaxpy7jnkxyiemmhwpryyksqmd foreign key (idCliente) references Cliente;
alter table if exists Pedido
    add constraint FKbkxcqmryxiew8pg5giyhan95m foreign key (idEntregador) references Perfil;
alter table if exists Perfil
    add constraint FK5ucdwy5ukx2od0sj9ego7h6tj foreign key (idFuncionario) references Funcionario;
alter table if exists Produto
    add constraint FK6081i713vd7gx1ki0ub2d86hg foreign key (empresa_id) references Empresa;
alter table if exists Pedido
    add constraint FK6081i713vd7gx1ki0ub2d86hy foreign key (idEmpresa) references Empresa;

-- Inserindo as Empresas
INSERT INTO Empresa (cnpj, cpf, nome, porcentagemComissaoEntregador) VALUES
('12345678000100', NULL, 'Empresa A', 5.0),
('98765432000111', NULL, 'Empresa B', 7.0);

-- Inserindo Funcionários para as Empresas
INSERT INTO Funcionario (idEmpresa, nome, senha, telefone) VALUES
(1, 'Admin Empresa A', /* senhaAdminA */ '7bc2a7eca0697f9bf1328affa905eb353d878d32b219c789ebdfb643adf55c40', '11111111111'),
(2, 'Admin Empresa B', /* senhaAdminB */ '0f91960fef508732c48a5c06719e78ec88679dfbe22967472eeabfea63463cc6', '22222222222');

-- Inserindo Perfis para os Funcionários
INSERT INTO Perfil (idFuncionario, tipoPerfil) VALUES
(1, 'ADMINISTRADOR'),
(2, 'ADMINISTRADOR');
