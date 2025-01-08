
![Logo](https://i.ibb.co/NZPkRT0/logo.png)
# Vence Tudo: Semente

Este aplicativo Android, desenvolvido em Kotlin com Jetpack Compose, auxilia na configuração de plantadeiras Vence Tudo, calculando as configuração ideal de engrenagens para a distribuição de sementes solicitada pelo usuário.

A verfificação na tabela da plantadeira é demorada e limitada a alguns discos especificos. Essa ferramenta se adapta a todos os discos.
## Funcionalidades

- Cálculo preciso das engrenagens com base no número de sementes por metro linear e número de furos no disco.
- Opções de ajuste para encontrar a configuração mais adequada.
- Interface intuitiva e fácil de usar.
## Algoritmo

Esse aplicativo recebe o valor de sementes na qual se deseja distribuir por metro linear e o número de furos no disco. Com essas informações e uma constante aqui nomeada *ValorVT* que representa a relação base entre a roda da plantadeira e a base do disco, é buscada todas as combinações de engrenagens possiveis, então são apresentados os valores mais próximos do objetivo.
## Como Usar

Primeiro informe o número de sementes por metro linear que você deseja distribuir. Depois o número de furos no disco que esta sendo utilizado. Após isso é só apartar em "Calcular"

![Tela Inical](https://i.ibb.co/ThSRrDF/inicial.png)

Como nem sempre o valor exato pode ser obtido alguns parâmetros de ajustes podem ser usados.
Pode ser buscado o valor mais próximo a cima ou mais próximo abaixo do valor inserido.

![Acima Abaixo](https://i.ibb.co/Nx5sYTD/arredonda.png)

Também existe a possibilidade de ajuste mudando apenas 2 dos 4 eixos na plantadeira, sendo assim uma regulagem mais fácil, no entanto possivelmente menos precisa.

![Precisao](https://i.ibb.co/ykxcFKT/tipo.png)

Esses parâmetros podem ser ajustadas rapidamente e assim o operador decide qual se aqueda melhor ao seu trabalho.

Ele foi desenvolvido com base na plantadeira Vence Tudo Panther ano 2016, mas creio que o mesmo deve ser compatível com os outros modelos da mesma linha. Para facilitar a compreensão foi adotado um padrão de nomear os eixos como A, B, C e D. Como demonstrado na imagem abaixo:

![Engrenagens](https://i.ibb.co/GpytKfR/eng.png)

O aplicativo vai mostrar qual engrenagem deve estar em cada eixo para se obter o resultado solicitado, bem como qual será o valor real de sementes que serão distribuidas por metro linear.

![Resultado](https://i.ibb.co/fVJcbMV/resultado.png)
## Licença

[Mozilla Public License 2.0](https://choosealicense.com/licenses/mpl-2.0/)
## Direito de imagem e marca

Todas as marcas pertencem a Vence Tudo - Ind. de Máquinas e Implementos Agrícolas\
[Vence Tudo](https://vencetudo.ind.br/)