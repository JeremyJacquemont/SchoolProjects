<!-- $Id: site_faq_nl.html 1012 2009-02-06 13:11:29Z jberanek $ -->

<div id="site_faq_contents">
  <a name="top"></a><h4>Authenticatie</h4>
  <ul>
    <li><a href="<?php echo $_SERVER[REQUEST_URI]; ?>#authenticate">Hoe log ik in?</a></li>
    <li><a href="<?php echo $_SERVER[REQUEST_URI]; ?>#meeting_delete">Waarom kan ik een boeking niet aanpassen of verwijderen?</a></li>
  </ul>

  <h4>Maken/Wijzigen boekingen</h4>
  <ul>
    <li><a href="<?php echo $_SERVER[REQUEST_URI]; ?>#repeating">Hoe plaats ik een herhalende boeking?</a></li>
    <li><a href="<?php echo $_SERVER[REQUEST_URI]; ?>#repeating_delete">Hoe verwijder ik een boeking uit een serie?</a></li>
    <li><a href="<?php echo $_SERVER[REQUEST_URI]; ?>#multiple_sites">Hoe plaats ik een boeking op verschillende plaatsen?</a></li>
    <li><a href="<?php echo $_SERVER[REQUEST_URI]; ?>#too_many">Mijn boeking mislukte vanwege <q>te veel boekingen</q>!</a></li>
    <li><a href="<?php echo $_SERVER[REQUEST_URI]; ?>#multiple_users">Wat gebeurt er als meerdere mensen op dezelfde datum/tijd/plaats een boeking willen maken?</a></li>
  </ul>

  <h4>Diversen</h4>
  <ul>
    <li><a href="<?php echo $_SERVER[REQUEST_URI]; ?>#internal_external">Wat is het verschil tussen <q>Intern</q> en <q>Extern</q>?</a></li>
  </ul>

  <h4>Over het Meeting Room Booking System</h4>
  <ul>
    <li><a href="<?php echo $_SERVER[REQUEST_URI]; ?>#how_much">Wat heeft dit systeem gekost?</a></li>
    <li><a href="<?php echo $_SERVER[REQUEST_URI]; ?>#about">Hoe werkt het en wie heeft het gemaakt?</a></li>
  </ul>
</div>

<div id="site_faq_body">

  <div id="authenticate">
    <h4>Hoe log ik in?</h4>
    <p>
      Het systeem kan worden ingesteld om met verschillende manieren van authenticatie te werken,
      waaronder LDAP, Netware, en SMB.<br>Neem contact op met je systeembeheerder als je problemen
      hebt met inloggen.<br>Sommige functies zijn beperkt tot bepaalde gebruikers en andere gebruikers
      zullen de melding <q>U heeft geen rechten om deze boeking aan te passen.</q>.<br>
      Neem contact op met je systeembeheerder als dit niet goed gaat voor jou.<br>
      Als het systeem is ingesteld met LDAP authenticatie betekent dat, dat je inlogt met dezelfde
      gebruikersnaam en wachtwoordd als je bebruikt voor je email b.v. <q>Gebruiker</q> en <q>Wachtwoord</q>.
    </p>
    <a href="<?php echo $_SERVER[REQUEST_URI]; ?>#top">Top</a>
  </div>

  <div id="meeting_delete">
    <h4>Waarom kan ik een boeking niet aanpassen of verwijderen?</h4>
    <p>
      Alleen de oorspronkelijke boeker kan een boeking verwijderen of aanpassen.<br>
      Neem contact op met de beheerder of de oorspronkelijke boeker.<br>
      Als je nadat je een boeking hebt geplaatst en later je login naam hebt aangepast
      kun je een oude boeking ook niet meer verwijderen of aanpassen.<br>
      Neem contact op met de beheerder om de boeking te verwijderen of aan te passen.
    </p>
    <a href="<?php echo $_SERVER[REQUEST_URI]; ?>#top">Top</a>
  </div>

  <div id="repeating">
    <h4>Hoe plaats ik een herhalende boeking?</h4>
    <p>
      Een klik op de gewenste tijd brengt je in het boeking scherm.
      Kies het betreffende <dfn>Soort Herhaling</dfn>. De boeking wordt geplaatst op
      dezelfde tijd tot en met de <dfn>Einde Herhaling datum</dfn>, op de dagen als bepaald door de
      Soort Herhaling.
    </p>
    <ul>
      <li>
        Een <dfn>Dagelijks</dfn> herhaalde boeking herhaalt elke dag.
      </li>
      <li>
        Een <dfn>Wekelijks</dfn> herhaalde boeking herhaalt op elke dag van de week die
        wordt aangevinkt bij <dfn>Herhaal Dag</dfn>.<br>
        Bij voorbeeld gebruik Wekelijks om een boeking te plaatsen voor maandag,
        dinsdag en donderdag; Klik die dagen aan bij Herhaal Dag. Als maar &eacute;&eacute;n dag wordt
        aangeklikt zal alleen die dag worden geplaatst.
      </li>
      <li>
        Een <dfn>Maandelijks</dfn> herhaalde boeking herhaalt op dezelfde dag van de maand,
        bijvoorbeeld de 15e van de maand.
      </li>
      <li>
        Een <dfn>Jaarlijks</dfn> herhaalde boeking herhaalt op dezelfde maand en dag,
        bijvoorbeeld elke 15e maart.
      </li>
      <li>
        Een <dfn>Maandelijks, Overeenkomstige dag</dfn> herhaalde boeking herhaalt een dag per maand
        op dezelfde weekdag en postie in de maand.
        Gebruik deze herhaling als je de eerste maandag, de tweede dinsdag of de vierde vrijdag van de
        maand wilt boeken.
        Gebruik dit type niet voor boekingen na de 28e van de maand!
      </li>
      <li>
        Tenslotte, een <dfn>n-Wekelijks</dfn> herhaalde boeking herhaalt op elke dag van de week die
        wordt aangevinkt bij <dfn>Herhaal Dag</dfn> met tussenpozen van het aantal weken zoals opgegeven bij n-wekelijks.<br>
        Gebruik deze herhaling als je om de 3 weken op dinsdag wilt boeken.
      </li>
    </ul>
    <a href="<?php echo $_SERVER[REQUEST_URI]; ?>#top">Top</a>
  </div>

  <div id="repeating_delete">
    <h4>Hoe verwijder ik een boeking uit een serie??</h4>
    <p>
      Selecteer de dag/boeking die je wilt verwijderen en kies <dfn>Boeking Verwijderen</dfn>.
    </p>
    <a href="<?php echo $_SERVER[REQUEST_URI]; ?>#top">Top</a>
  </div>

  <div id="multiple_sites">
    <h4>Hoe plaats ik een boeking op verschillende plaatsen?</h4>
    <p>
      Niet. Het huidige systeem kan niet twee verschillende boekingen tegelijk
      plaatsen. Je zult ze apart moeten boeken. Controleer of de tijd op de andere
      plaats beschikbaar is, voor je een boeking plaatst.
    </p>
    <a href="<?php echo $_SERVER[REQUEST_URI]; ?>#top">Top</a>
  </div>

  <div id="too_many">
    <h4>Mijn boeking mislukte vanwege <q>te veel boekingen</q>!</h4>
    <p>
      Een serie kan niet meer dan 365 boekingen plaatsen. Er moet nu eenmaal een grens worden bepaald.
      De systeembeheerder kan het aantal aanpassen als dat nodig mocht zijn.
    </p>
    <a href="<?php echo $_SERVER[REQUEST_URI]; ?>#top">Top</a>
  </div>

  <div id="multiple_users">
    <h4>Wat gebeurt er als meerdere mensen dezelfde boeking willen maken?</h4>
    <p>
      Het korte antwoord: Wie het eerst op <dfn>Opslaan</dfn> klikt wint.
      Intern gebruikt het systeem een relationele database met multi-user en multi-threading
      capaciteiten die duizenden gebruikers kan bedienen.
    </p>
    <a href="<?php echo $_SERVER[REQUEST_URI]; ?>#top">Top</a>
  </div>

  <div id="internal_external">
    <h4>Wat is het verschil tussen <q>Intern</q> en <q>Extern</q>?</h4>
    <p>
      Standaard komt, MRBS met twee boekingstypes. <dfn>Intern</dfn> betekent dat de
      boeking bedoeld is voor medewerkers. <dfn>Extern</dfn> betekent dat ook mensen van
      buiten de organisatie als klanten, leveranciers e.d. aanwezig kunnen zijn.
      Er kunnen in totaal 10 types worden gedefinieerd, afhankelijk van de behoefte.
      Per type wordt een kleur ingesteld die zichtbaar is in het algemene kalender overzicht.
      Een legenda van de kleuren en gedefinieerde typen word aan de onderkant van het algemene
      kalender overzicht getoond.
    </p>
    <a href="<?php echo $_SERVER[REQUEST_URI]; ?>#top">Top</a>
  </div>

  <div id="how_much">
    <a></a><h4>Wat heeft dit systeem gekost?</h4>
    <p>
      Niets.  Zie de volgende sectie voor meer informatie.
    </p>
    <a href="<?php echo $_SERVER[REQUEST_URI]; ?>#top">Top</a>
  </div>

  <div id="about">
    <h4>Hoe werkt het en wie heeft het gemaakt?</h4>
    <p>
      <a href="http://sourceforge.net/project/?group_id=5113">Meeting Room Booking System (MRBS)</a>
      is open source software dat wordt gedistribueerd onder de 'Gnu Public License(GPL)'.
      Dit betekent dat de software vrij is te gebruiken, te verspreiden en aan te passen.
    </p>
    <p>
      Het systeem is vooral geschreven in <a href="http://www.php.net">PHP</a>,
      een open source programmeertaal, dat kan worden gebruikt in web-pagina's op een zelfde
      manier als Microsoft 'active server pages'.  PHP is vooral sterk in het benaderen van
      databases.
    </p>
    <p>
      De database gebruikt voor het systeem is <a href="http://www.mysql.com">MySQL</a>
      of <a href="http://www.postgresql.org">PostgreSQL</a>.<br>
      MySQL is een erg snelle, multi-threaded, multi-user and robuuste
      SQL (Structured Query Language) database server die ook gebruikt maakt van GPL.<br>
      PostgreSQL is een volledige geimplementeerde multi-user open source Object Relationele SQL
      database server.
    </p>
    <p>
      Het systeem draait op verschillende platformen, inclusief PC's die gebruik maken van
      <a href="http://www.linux.com">Linux</a>.
      Linux, is een gratis, open source, unix-achtig operating systeem.
    </p>
    <p>
      De web server die gebruikt wordt is ook gratis, open source software.
      De <a href="http://www.apache.org">Apache</a> web server is meest populaire
      web server ter wereld.
    </p>
    <p>
      Het eind van het verhaal is: <strong>Elk onderdeel van dit systeem, van het operating systeem tot de applicatie,
      is geheel vrij en gratis - tot aan de source code!</strong>.
    </p>
    <a href="<?php echo $_SERVER[REQUEST_URI]; ?>#top">Top</a>
  </div>
</div>
