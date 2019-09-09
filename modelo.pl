% modelo

% ------------ Fatos SERÃO GERADOS via asserta no java Chatter


 % ------------ Regras
%permutation 
remove([X|Xs],X,Xs).
remove([X|Xs],E,[X|Ys]):-remove(Xs,E,Ys).
permutation([],[]).
permutation(Xs,[X|Ys]):-remove(Xs,X,Zs),permutation(Zs, Ys).

enesimo(1,X,[X|_]).      	%  Este predicado devolve em X o enésimo elemento de
enesimo(N,X,[_|Y]):-      	%  uma lista.  Pode ser usado no sentido inverso para
	enesimo(M,X,Y),   	%  informar a posição de um determinado elemento
	N is M + 1.        	%  na lista.

	
% Dependencia Estrita
dependenciaEstrita([X|Xs]) :- depende(A,B),
enesimo(IdxA,A,[X|Xs]),
enesimo(IdxB,B,[X|Xs]),
IdxA > IdxB
.

% modelo é montado via codigo dinamicamente dependendo da existencia das regras Situations no codigo.
gerarModelo(Dominio, Modelo) :- permutation(Dominio, Modelo).
gerarModeloDe(Dominio, Modelo) :- permutation(Dominio, Modelo), dependenciaEstrita(Modelo).

