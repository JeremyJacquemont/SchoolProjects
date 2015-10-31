using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace GSB
{
    class Functions
    {
        /// <summary>
        /// Item Class
        /// </summary>
        public class Item
        {
            public string Name;
            public string Value;
            public Item(string name, string value)
            {
                Name = name; Value = value;
            }
            public override string ToString()
            {
                // Generates the text shown in the combo box
                return Name;
            }
        }

        /// <summary>
        /// Function for get user
        /// </summary>
        /// <param name="login"> String </param>
        /// <param name="password"> String </param>
        /// <returns> if(user) return user; else return string error;</returns>
        public static Object getUser(string login, string password)
        {
            var ctx = new dbDataContext();
            try
            {
                var user = (from v in ctx.visiteur
                            where v.login == login && v.mdp == password
                            select v).First();
                return user;
            }
            catch (Exception)
            {
                return "Login et/ou mot de passe incorrect";
            }
        }

        /// <summary>
        /// Function for return date less one year
        /// </summary>
        /// <param name="date"> DateTime </param>
        /// <returns> DateTime </returns>
        public static DateTime getDateLessOneYear(DateTime date)
        {
            return date - (DateTime.Now.AddYears(1) - DateTime.Now);
        }

        /// <summary>
        /// Function convert date in correct format
        /// </summary>
        /// <param name="date"> String </param>
        /// <returns> String </returns>
        public static String getDateWithString(String date)
        {
            return date.Substring(0, 2) + "/" + date.Substring(2, 4);
        }

        /// <summary>
        /// Function for get ligne Forfaits in fiche
        /// <param name="ctx"> dbDataContext </param>
        /// <param name="user"> visiteur </param>
        /// </summary>
        public static Object getLignesForfaits(dbDataContext ctx, visiteur user)
        {
            return from l in ctx.lignefraisforfait
                   where l.idVisiteur == user.id
                   select new { Frais = l.fraisforfait.libelle, Quantite = l.quantite };
        }

        /// <summary>
        /// Function for get ligne Forfaits in fiche
        /// <param name="ctx"> dbDataContext </param>
        /// <param name="user"> Item </param>
        /// <param name="date"> String </param>
        /// </summary>
        public static Object getLignesForfaits(dbDataContext ctx, Item user, string date)
        {
            return from l in ctx.lignefraisforfait
                    where l.idVisiteur == user.Value && l.mois == date
                   select new { Frais = l.fraisforfait.libelle, Quantite = l.quantite };
        }

        /// <summary>
        /// Function for get ligne Forfaits in fiche
        /// <param name="ctx"> dbDataContext </param>
        /// <param name="user"> visiteur </param>
        /// <param name="date"> String </param>
        /// </summary>
        public static Object getLignesForfaits(dbDataContext ctx, visiteur user, string date)
        {
            return from l in ctx.lignefraisforfait
                   where l.idVisiteur == user.id && l.mois == date
                   select new { Frais = l.fraisforfait.libelle, Quantite = l.quantite };
        }

        /// <summary>
        /// Function for get ligne Hors Forfaits in fiche
        /// <param name="ctx"> dbDataContext </param>
        /// <param name="user"> visiteur </param>
        /// </summary>
        public static Object getLignesHorsForfaits(dbDataContext ctx, visiteur user)
        {
            return from l in ctx.lignefraishorsforfait
                   where l.idVisiteur == user.id
                   select new { Libelle = l.libelle, Date = l.date, Montant = l.montant };
        }

        /// <summary>
        /// Function for get ligne Hors Forfaits in fiche
        /// <param name="ctx"> dbDataContext </param>
        /// <param name="user"> visiteur </param>
        /// <param name="date"> String </param>
        /// </summary>
        public static Object getLignesHorsForfaits(dbDataContext ctx, visiteur user, string date)
        {
            return from l in ctx.lignefraishorsforfait
                   where l.idVisiteur == user.id && l.mois == date
                   select new { Libelle = l.libelle, Date = l.date, Montant = l.montant };
        }

        /// <summary>
        /// Function for get ligne Hors Forfaits in fiche
        /// <param name="ctx"> dbDataContext </param>
        /// <param name="user"> Item </param>
        /// <param name="date"> String </param>
        /// </summary>
        public static Object getLignesHorsForfaits(dbDataContext ctx, Item user, string date)
        {
            return from l in ctx.lignefraishorsforfait
                   where l.idVisiteur == user.Value && l.mois == date
                   select new { Libelle = l.libelle, Date = l.date, Montant = l.montant };
        }

        /// <summary>
        /// Function for add a new fiche in BDD
        /// </summary>
        /// <param name="ctx"> dbDataContext </param>
        /// <param name="idUser"> String </param>
        /// <param name="date"> String </param>
        /// <param name="submit"> Boolean </param>
        public static void insertNewFiche(dbDataContext ctx, string idUser, string date, bool submit)
        {
            var fiche = new fichefrais
            {
                idVisiteur = idUser,
                mois = date,
                nbJustificatifs = 0,
                montantValide = 0,
                dateModif = DateTime.Now,
                idEtat = "CR"
            };
            ctx.fichefrais.InsertOnSubmit(fiche);

            var req = from f in ctx.fraisforfait
                      select f;

            foreach (var element in req)
            {
                var ligneFrais = new lignefraisforfait
                {
                    idVisiteur = idUser,
                    mois = date,
                    idFraisForfait = element.id,
                    quantite = 0
                };
                ctx.lignefraisforfait.InsertOnSubmit(ligneFrais);
            }

            if (submit)
            {
                ctx.SubmitChanges();
            }
        }
    }
}
