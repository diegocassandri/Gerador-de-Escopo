insert into usuario (codigo, email, mudarSenha, nomeCompleto, senha, status, usuario) values(seq_usuario.nextval,'prodama@prodama.com.br','','Prodama','cHJvZGFtYQ==','ATIVO','prodama');
insert into grupo (codigo,descricao) values (seq_grupo.nextval,'Master');

insert into estado values (seq_estado.nextval,'Paraná','PR');
insert into cidade values(seq_cidade.nextval,'Umuarama',1);

insert into tela (codigo,descricao,url,tela_pai) values( seq_tela.nextval,'Cadastros','/cadastros.xhtml',null);
insert into tela (codigo,descricao,url,tela_pai) values( seq_tela.nextval,'Cadastros Projetos','/projetos',null);
insert into tela (codigo,descricao,url,tela_pai) values( seq_tela.nextval,'Produtos','/cadastros/produtos',null);

insert into tela (codigo,descricao,url,tela_pai) values( seq_tela.nextval,'Cadastro Grupo Usuário','/cadastros/CadastroGrupoUsuario.xhtml',1);
insert into tela (codigo,descricao,url,tela_pai) values( seq_tela.nextval,'Cadastro Telas do Sistema','/cadastros/CadastroTela.xhtml',1);
insert into tela (codigo,descricao,url,tela_pai) values( seq_tela.nextval,'Cadastro Estado','/cadastros/CadastroEstado.xhtml',1);
insert into tela (codigo,descricao,url,tela_pai) values( seq_tela.nextval,'Cadastro Cidade','/cadastros/CadastroCidade.xhtml',1);
insert into tela (codigo,descricao,url,tela_pai) values( seq_tela.nextval,'Cadastro Empresa','/cadastros/CadastroEmpresa.xhtml',1);
insert into tela (codigo,descricao,url,tela_pai) values( seq_tela.nextval,'Cadastro Filial','/cadastros/CadastroFilial.xhtml',1);

insert into tela (codigo,descricao,url,tela_pai) values( seq_tela.nextval,'Cadastro Tipo de Hora','/cadastros/projetos/CadastroTipoDeHora.xhtml',2);
insert into tela (codigo,descricao,url,tela_pai) values( seq_tela.nextval,'Cadastro Empresa Cliente','/cadastros/projetos/CadastroEmpresaCliente.xhtml',2);
insert into tela (codigo,descricao,url,tela_pai) values( seq_tela.nextval,'Cadastro Filial Cliente','/cadastros/projetos/CadastroFilialCliente.xhtml',2);
insert into tela (codigo,descricao,url,tela_pai) values( seq_tela.nextval,'Cadastro Usuário','/cadastros/CadastroUsuario.xhtml',2);
insert into tela (codigo,descricao,url,tela_pai) values( seq_tela.nextval,'Cadastro de Equipe','/cadastros/projetos/CadastroEquipe.xhtml',2);
insert into tela (codigo,descricao,url,tela_pai) values( seq_tela.nextval,'Nível de Equipe','/cadastros/projetos/CadastroNivelEquipe.xhtml',2);
insert into tela (codigo,descricao,url,tela_pai) values( seq_tela.nextval,'Faixa de Colaborador','/cadastros/projetos/CadastroFaixaColaborador.xhtml',2);

insert into tela (codigo,descricao,url,tela_pai) values( seq_tela.nextval,'Cadastro de Produtos','/cadastros/produtos/CadastroProduto.xhtml',3);
insert into tela (codigo,descricao,url,tela_pai) values( seq_tela.nextval,'Cadastro Módulo','/cadastros/produtos/CadastroModulo.xhtml',3);
insert into tela (codigo,descricao,url,tela_pai) values( seq_tela.nextval,'Gestão do Módulo','/cadastros/produtos/CadastroGestaoModulo.xhtml',3);

insert into usuario_grupo values (1,1);
insert into grupo_tela values (1,1);
insert into grupo_tela values (1,2);
insert into grupo_tela values (1,3);
insert into grupo_tela values (1,4);
insert into grupo_tela values (1,5);
insert into grupo_tela values (1,6);
insert into grupo_tela values (1,7);
insert into grupo_tela values (1,8);
insert into grupo_tela values (1,9);
insert into grupo_tela values (1,10);
insert into grupo_tela values (1,11);
insert into grupo_tela values (1,12);
insert into grupo_tela values (1,13);
insert into grupo_tela values (1,14);
insert into grupo_tela values (1,15);
insert into grupo_tela values (1,16);
insert into grupo_tela values (1,17);
insert into grupo_tela values (1,18);
insert into grupo_tela values (1,19);

