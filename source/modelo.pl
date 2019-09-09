% modelo.pl
% Arquivo contendo as Cláusulas prolog para o Chatterbot

% ------------ Fatos SERÃO GERADOS DINAMICAMENTE via asserta no java Chatter


 % ------------ Regras
%permutation 
remove([X|Xs],X,Xs).
remove([X|Xs],E,[X|Ys]):-remove(Xs,E,Ys).
permutation([],[]).
permutation(Xs,[X|Ys]):-remove(Xs,X,Zs),permutation(Zs, Ys).

enesimo(1,X,[X|_]).      	%  devolve em X o enésimo elemento de uma lista. Pode ser usado no sentido inverso para informar a posição de um elemento na lista.
enesimo(N,X,[_|Y]):-      	    
	enesimo(M,X,Y),   	  
	N is M + 1.        	  

	
% Dependencia Estrita
dependenciaEstrita([X|Xs]) :- depende(A,B),
enesimo(IdxA,A,[X|Xs]),
enesimo(IdxB,B,[X|Xs]),
IdxA > IdxB
.

% Os modelos são gerados dinâmicamente dependendo da existencia das regras Situations.
gerarModelo(Dominio, Modelo) :- permutation(Dominio, Modelo).
gerarModeloDe(Dominio, Modelo) :- permutation(Dominio, Modelo), dependenciaEstrita(Modelo).

