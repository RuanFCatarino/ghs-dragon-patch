# GHS Dragon Patch

Complemento Fabric 26.3 para **Skywyrms** e **Useless Reptile**. O identificador
interno `ghs_dragon_safety` e o nome do arquivo `ghs-dragon-safety` permanecem
iguais para que o launcher e as instalacoes existentes reconhecam o mod.

## Funcoes atuais

- Skywyrms: descida segura durante o voo com tres toques em Shift.
- Useless Reptile: descida segura com tres toques em Shift e aceleracao em voo
  com Shift pressionado.
- Correcoes de entrada, tela do dragao e conflito entre agachar, rastejar e
  ampliar o minimapa.

As integracoes com cada mod sao opcionais. O `DragonMixinPlugin` ativa os
ajustes especificos apenas quando o mod correspondente esta instalado.

## Proxima extensao: tela de ordens para Skywyrms

Implementar uma interface propria, sem copiar a tela do Useless Reptile:

1. Abrir apenas para o dono do dragao e validar a distancia no servidor.
2. Mostrar nome, vida, sela e estado de seguir/ficar.
3. Enviar cada ordem ao servidor; nunca confiar apenas no clique do cliente.
4. Persistir qualquer modo novo (por exemplo, vagar) no dado do dragao.
5. Se houver inventario, criar armazenamento real com salvamento e recuperacao
   dos itens, incluindo morte, descarregamento do mundo e atualizacao do mod.
6. Testar em copia do mundo antes de incluir no pacote do launcher e servidor.

O codigo do Useless Reptile pode servir de referencia conforme as permissoes
publicadas pelo autor; o patch nao incorpora recursos ou arquivos desse mod.

## Compilacao

JDK 25: `./gradlew check jar`.
