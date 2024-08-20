CREATE TABLE PERFIL (ID  SERIAL NOT NULL, tipoPerfil VARCHAR(31), idFuncionario BIGINT NOT NULL, PRIMARY KEY (ID))
CREATE TABLE CLIENTE (ID  SERIAL NOT NULL, BAIRRO VARCHAR(255) NOT NULL, CNPJ VARCHAR(255), CPF VARCHAR(255), LOGRADOURO VARCHAR(255) NOT NULL, NOME VARCHAR(255) NOT NULL, TELEFONE VARCHAR(255) NOT NULL, idEmpresa BIGINT NOT NULL, PRIMARY KEY (ID))
CREATE TABLE EMPRESA (ID  SERIAL NOT NULL, CNPJ VARCHAR(255), CPF VARCHAR(255), NOME VARCHAR(255) NOT NULL, PORCENTAGEMCOMISSAOENTREGADOR FLOAT, PRIMARY KEY (ID))
CREATE TABLE FUNCIONARIO (ID  SERIAL NOT NULL, NOME VARCHAR(255) NOT NULL, SENHA VARCHAR(255) NOT NULL, TELEFONE VARCHAR(255) NOT NULL UNIQUE, idEmpresa BIGINT NOT NULL, PRIMARY KEY (ID))
CREATE TABLE ITEMPEDIDO (ID  SERIAL NOT NULL, QUANTIDADE INTEGER, VALORUNITARIO FLOAT, idPedido BIGINT, idProduto BIGINT, PRIMARY KEY (ID))
CREATE TABLE PEDIDO (ID  SERIAL NOT NULL, DATA DATE, OBSERVACOES VARCHAR(255), STATUS INTEGER, VALORTOTAL FLOAT, idCliente BIGINT, idEntregador BIGINT, PRIMARY KEY (ID))
CREATE TABLE PRODUTO (ID  SERIAL NOT NULL, LOCALIZACAO VARCHAR(255), NOME VARCHAR(255) NOT NULL, EMPRESA_ID BIGINT, PRIMARY KEY (ID))
ALTER TABLE PERFIL ADD CONSTRAINT FK_PERFIL_idFuncionario FOREIGN KEY (idFuncionario) REFERENCES FUNCIONARIO (ID)
ALTER TABLE CLIENTE ADD CONSTRAINT FK_CLIENTE_idEmpresa FOREIGN KEY (idEmpresa) REFERENCES EMPRESA (ID)
ALTER TABLE FUNCIONARIO ADD CONSTRAINT FK_FUNCIONARIO_idEmpresa FOREIGN KEY (idEmpresa) REFERENCES EMPRESA (ID)
ALTER TABLE ITEMPEDIDO ADD CONSTRAINT FK_ITEMPEDIDO_idPedido FOREIGN KEY (idPedido) REFERENCES PEDIDO (ID)
ALTER TABLE ITEMPEDIDO ADD CONSTRAINT FK_ITEMPEDIDO_idProduto FOREIGN KEY (idProduto) REFERENCES PRODUTO (ID)
ALTER TABLE PEDIDO ADD CONSTRAINT FK_PEDIDO_idCliente FOREIGN KEY (idCliente) REFERENCES CLIENTE (ID)
ALTER TABLE PEDIDO ADD CONSTRAINT FK_PEDIDO_idEntregador FOREIGN KEY (idEntregador) REFERENCES PERFIL (ID)
ALTER TABLE PRODUTO ADD CONSTRAINT FK_PRODUTO_EMPRESA_ID FOREIGN KEY (EMPRESA_ID) REFERENCES EMPRESA (ID)
create table Cliente (id bigserial not null, idEmpresa bigint not null, bairro varchar(255) not null, cnpj varchar(255), cpf varchar(255), logradouro varchar(255) not null, nome varchar(255) not null, telefone varchar(255) not null, primary key (id));
create table Empresa (porcentagemComissaoEntregador float(53), id bigserial not null, cnpj varchar(255) unique, cpf varchar(255) unique, nome varchar(255) not null, primary key (id));
create table Funcionario (id bigserial not null, idEmpresa bigint not null, nome varchar(255) not null, senha varchar(255) not null, telefone varchar(255) not null unique, primary key (id));
create table ItemPedido (quantidade integer, valorUnitario float(53), id bigserial not null, idPedido bigint, idProduto bigint, primary key (id));
create table Pedido (data date, valorTotal float(53), id bigserial not null, idCliente bigint, idEntregador bigint, observacoes varchar(255), status varchar(255) check (status in ('EM_PREPARACAO','SAIU_PARA_ENTREGA','ENTREGUE')), primary key (id));
create table Perfil (id bigserial not null, idFuncionario bigint not null, tipoPerfil varchar(31) not null, primary key (id));
create table Produto (empresa_id bigint, id bigserial not null, localizacao varchar(255), nome varchar(255) not null, primary key (id));
alter table if exists Cliente add constraint FKpho1xugjo8nurag2egy0dsxg2 foreign key (idEmpresa) references Empresa;
alter table if exists Funcionario add constraint FKlup6mu3v6rc8nu4gda1gx2oay foreign key (idEmpresa) references Empresa;
alter table if exists ItemPedido add constraint FKijirvim8g8orndencmvk3eije foreign key (idPedido) references Pedido;
alter table if exists ItemPedido add constraint FKln7funvfobr6kvr0td9wo3sef foreign key (idProduto) references Produto;
alter table if exists Pedido add constraint FKaxpy7jnkxyiemmhwpryyksqmd foreign key (idCliente) references Cliente;
alter table if exists Pedido add constraint FKbkxcqmryxiew8pg5giyhan95m foreign key (idEntregador) references Perfil;
alter table if exists Perfil add constraint FK5ucdwy5ukx2od0sj9ego7h6tj foreign key (idFuncionario) references Funcionario;
alter table if exists Produto add constraint FK6081i713vd7gx1ki0ub2d86hg foreign key (empresa_id) references Empresa;
create table Cliente (id bigserial not null, idEmpresa bigint not null, bairro varchar(255) not null, cnpj varchar(255), cpf varchar(255), logradouro varchar(255) not null, nome varchar(255) not null, telefone varchar(255) not null, primary key (id));
create table Empresa (porcentagemComissaoEntregador float(53), id bigserial not null, cnpj varchar(255) unique, cpf varchar(255) unique, nome varchar(255) not null, primary key (id));
create table Funcionario (id bigserial not null, idEmpresa bigint not null, nome varchar(255) not null, senha varchar(255) not null, telefone varchar(255) not null unique, primary key (id));
create table ItemPedido (quantidade integer, valorUnitario float(53), id bigserial not null, idPedido bigint, idProduto bigint, primary key (id));
create table Pedido (data date, valorTotal float(53), id bigserial not null, idCliente bigint, idEntregador bigint, observacoes varchar(255), status varchar(255) check (status in ('EM_PREPARACAO','SAIU_PARA_ENTREGA','ENTREGUE')), primary key (id));
create table Perfil (id bigserial not null, idFuncionario bigint not null, tipoPerfil varchar(31) not null, primary key (id));
create table Produto (empresa_id bigint, id bigserial not null, localizacao varchar(255), nome varchar(255) not null, primary key (id));
alter table if exists Cliente add constraint FKpho1xugjo8nurag2egy0dsxg2 foreign key (idEmpresa) references Empresa;
alter table if exists Funcionario add constraint FKlup6mu3v6rc8nu4gda1gx2oay foreign key (idEmpresa) references Empresa;
alter table if exists ItemPedido add constraint FKijirvim8g8orndencmvk3eije foreign key (idPedido) references Pedido;
alter table if exists ItemPedido add constraint FKln7funvfobr6kvr0td9wo3sef foreign key (idProduto) references Produto;
alter table if exists Pedido add constraint FKaxpy7jnkxyiemmhwpryyksqmd foreign key (idCliente) references Cliente;
alter table if exists Pedido add constraint FKbkxcqmryxiew8pg5giyhan95m foreign key (idEntregador) references Perfil;
alter table if exists Perfil add constraint FK5ucdwy5ukx2od0sj9ego7h6tj foreign key (idFuncionario) references Funcionario;
alter table if exists Produto add constraint FK6081i713vd7gx1ki0ub2d86hg foreign key (empresa_id) references Empresa;
create table Cliente (id bigserial not null, idEmpresa bigint not null, bairro varchar(255) not null, cnpj varchar(255), cpf varchar(255), logradouro varchar(255) not null, nome varchar(255) not null, telefone varchar(255) not null, primary key (id));
create table Empresa (porcentagemComissaoEntregador float(53), id bigserial not null, cnpj varchar(255) unique, cpf varchar(255) unique, nome varchar(255) not null, primary key (id));
create table Funcionario (id bigserial not null, idEmpresa bigint not null, nome varchar(255) not null, senha varchar(255) not null, telefone varchar(255) not null unique, primary key (id));
create table ItemPedido (quantidade integer, valorUnitario float(53), id bigserial not null, idPedido bigint, idProduto bigint, primary key (id));
create table Pedido (data date, valorTotal float(53), id bigserial not null, idCliente bigint, idEntregador bigint, observacoes varchar(255), status varchar(255) check (status in ('EM_PREPARACAO','SAIU_PARA_ENTREGA','ENTREGUE')), primary key (id));
create table Perfil (id bigserial not null, idFuncionario bigint not null, tipoPerfil varchar(31) not null, primary key (id));
create table Produto (empresa_id bigint, id bigserial not null, localizacao varchar(255), nome varchar(255) not null, primary key (id));
alter table if exists Cliente add constraint FKpho1xugjo8nurag2egy0dsxg2 foreign key (idEmpresa) references Empresa;
alter table if exists Funcionario add constraint FKlup6mu3v6rc8nu4gda1gx2oay foreign key (idEmpresa) references Empresa;
alter table if exists ItemPedido add constraint FKijirvim8g8orndencmvk3eije foreign key (idPedido) references Pedido;
alter table if exists ItemPedido add constraint FKln7funvfobr6kvr0td9wo3sef foreign key (idProduto) references Produto;
alter table if exists Pedido add constraint FKaxpy7jnkxyiemmhwpryyksqmd foreign key (idCliente) references Cliente;
alter table if exists Pedido add constraint FKbkxcqmryxiew8pg5giyhan95m foreign key (idEntregador) references Perfil;
alter table if exists Perfil add constraint FK5ucdwy5ukx2od0sj9ego7h6tj foreign key (idFuncionario) references Funcionario;
alter table if exists Produto add constraint FK6081i713vd7gx1ki0ub2d86hg foreign key (empresa_id) references Empresa;
create table Cliente (id bigserial not null, idEmpresa bigint not null, bairro varchar(255) not null, cnpj varchar(255), cpf varchar(255), logradouro varchar(255) not null, nome varchar(255) not null, telefone varchar(255) not null, primary key (id));
create table Empresa (porcentagemComissaoEntregador float(53), id bigserial not null, cnpj varchar(255) unique, cpf varchar(255) unique, nome varchar(255) not null, primary key (id));
create table Funcionario (id bigserial not null, idEmpresa bigint not null, nome varchar(255) not null, senha varchar(255) not null, telefone varchar(255) not null unique, primary key (id));
create table ItemPedido (quantidade integer, valorUnitario float(53), id bigserial not null, idPedido bigint, idProduto bigint, primary key (id));
create table Pedido (data date, valorTotal float(53), id bigserial not null, idCliente bigint, idEntregador bigint, observacoes varchar(255), status varchar(255) check (status in ('EM_PREPARACAO','SAIU_PARA_ENTREGA','ENTREGUE')), primary key (id));
create table Perfil (id bigserial not null, idFuncionario bigint not null, tipoPerfil varchar(31) not null, primary key (id));
create table Produto (empresa_id bigint, id bigserial not null, localizacao varchar(255), nome varchar(255) not null, primary key (id));
alter table if exists Cliente add constraint FKpho1xugjo8nurag2egy0dsxg2 foreign key (idEmpresa) references Empresa;
alter table if exists Funcionario add constraint FKlup6mu3v6rc8nu4gda1gx2oay foreign key (idEmpresa) references Empresa;
alter table if exists ItemPedido add constraint FKijirvim8g8orndencmvk3eije foreign key (idPedido) references Pedido;
alter table if exists ItemPedido add constraint FKln7funvfobr6kvr0td9wo3sef foreign key (idProduto) references Produto;
alter table if exists Pedido add constraint FKaxpy7jnkxyiemmhwpryyksqmd foreign key (idCliente) references Cliente;
alter table if exists Pedido add constraint FKbkxcqmryxiew8pg5giyhan95m foreign key (idEntregador) references Perfil;
alter table if exists Perfil add constraint FK5ucdwy5ukx2od0sj9ego7h6tj foreign key (idFuncionario) references Funcionario;
alter table if exists Produto add constraint FK6081i713vd7gx1ki0ub2d86hg foreign key (empresa_id) references Empresa;
