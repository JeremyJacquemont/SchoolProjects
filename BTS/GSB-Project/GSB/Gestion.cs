using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace GSB
{
    public partial class Gestion : Form
    {
        /// <summary>
        /// Attributs
        /// </summary>
        private visiteur user;
        private dbDataContext ctx = new dbDataContext();
        private ContextMenuStrip mnu = new ContextMenuStrip();

        /// <summary>
        /// Constructor
        /// </summary>
        public Gestion()
        {
            InitializeComponent();
        }

        /// <summary>
        /// Constructor with visiteur
        /// </summary>
        /// <param name="v"> visiteur </param>
        public Gestion(visiteur v)
        {
            InitializeComponent();
            user = v;
        }

        /// <summary>
        /// Function for click in logout button
        /// </summary>
        /// <param name="sender"> Object </param>
        /// <param name="e"> Events </param>
        private void buttonLogout_Click(object sender, EventArgs e)
        {
            Connexion c = new Connexion();
            this.Hide();
            c.ShowDialog();
            this.Close();
        }

        /// <summary>
        /// Function for initialize Gestion Form
        /// </summary>
        /// <param name="sender"> Object </param>
        /// <param name="e"> Events </param>
        private void Gestion_Load(object sender, EventArgs e)
        {
            this.labelLogout.Text = "Vous êtes connecté en tant que " + user.nom.Replace(" ", string.Empty) + " " + user.prenom.Replace(" ", string.Empty) + ", visiteur médical";
            
            //Init comboBox
            initComboBox();

            //Init fiche
            initFiche();

            //Init all DataGridView's
            getFiches();
            initAddPanel();

            //Init contextMenu
            ToolStripMenuItem mnuSuppr = new ToolStripMenuItem("Supprimer");
            mnuSuppr.Click += mnuSuppr_Click;
            this.mnu.Items.AddRange(new ToolStripItem[] { mnuSuppr });
            labelAddFicheHorsFraisDataGridView.CellMouseDown += labelAddFicheHorsFraisDataGridView_CellMouseDown;
        }

        /// <summary>
        /// Function for context menu in DataGridView
        /// </summary>
        /// <param name="sender"> Object </param>
        /// <param name="e"> Events </param>
        private void labelAddFicheHorsFraisDataGridView_CellMouseDown(object sender, DataGridViewCellMouseEventArgs e)
        {
            // Ignore if a column or row header is clicked
            if (e.RowIndex != -1 && e.ColumnIndex != -1)
            {
                if (e.Button == MouseButtons.Right)
                {
                    DataGridViewCell clickedCell = (sender as DataGridView).Rows[e.RowIndex].Cells[e.ColumnIndex];

                    // Here you can do whatever you want with the cell
                    this.labelAddFicheHorsFraisDataGridView.CurrentCell = clickedCell;  // Select the clicked cell, for instance

                    // Get mouse position relative to the vehicles grid
                    var relativeMousePosition = labelAddFicheHorsFraisDataGridView.PointToClient(Cursor.Position);

                    // Show the context menu
                    this.mnu.Show(labelAddFicheHorsFraisDataGridView, relativeMousePosition);
                }
            }
        }

        /// <summary>
        /// Function for click in "Supprimer" button
        /// </summary>
        /// <param name="sender"> Object </param>
        /// <param name="e"> Events </param>
        private void mnuSuppr_Click(object sender, EventArgs e)
        {
            if (MessageBox.Show("Voulez-vous supprimer cette ligne?", "Confirmation", MessageBoxButtons.YesNo) == DialogResult.Yes)
            {
                // Try to cast the sender to a ToolStripItem
                ToolStripItem menuItem = sender as ToolStripItem;
                if (menuItem != null)
                {
                    // Retrieve the ContextMenuStrip that owns this ToolStripItem
                    ContextMenuStrip owner = menuItem.Owner as ContextMenuStrip;
                    if (owner != null)
                    {
                        // Get the control that is displaying this context menu
                        Control sourceControl = owner.SourceControl;
                        DataGridView dgv = sourceControl as DataGridView;

                        //Don't use the global function
                        var req = from l in this.ctx.lignefraishorsforfait
                                  where l.idVisiteur == user.id && l.mois == DateTime.Now.ToString("MMyyyy")
                                  select l;
                        List<lignefraishorsforfait> list = req.ToList();

                        //Remove lignehorsforfaits
                        this.ctx.lignefraishorsforfait.DeleteOnSubmit(list.ElementAt(dgv.CurrentCell.RowIndex));
                        this.ctx.SubmitChanges();
                        initFraisHorsForfaits();
                    }
                }
            }
        }

        /// <summary>
        /// Function for show View Panel
        /// </summary>
        /// <param name="sender"> Object </param>
        /// <param name="e"> Events </param>
        private void buttonView_Click(object sender, EventArgs e)
        {
            this.panelAdd.Visible = false;
            this.panelView.Visible = true;
        }

        /// <summary>
        /// Function for show Add Panel
        /// </summary>
        /// <param name="sender"> Object </param>
        /// <param name="e"> Events </param>
        private void buttonAdd_Click(object sender, EventArgs e)
        {
            this.panelView.Visible = false;
            this.panelAdd.Visible = true;
        }

        /// <summary>
        /// Function for draw a line
        /// </summary>
        /// <param name="e"> Events </param>
        protected override void OnPaint(PaintEventArgs e)
        {
            base.OnPaint(e);
            Graphics g;

            g = e.Graphics;

            Pen myPen = new Pen(Color.Black, 1);
            g.DrawLine(myPen, 0, this.buttonAdd.Location.Y + 40, 10000, this.buttonAdd.Location.Y + 40);
        }

        /// <summary>
        /// Function for add Frais
        /// </summary>
        /// <param name="sender"> Object </param>
        /// <param name="e"> Events </param>
        private void FicheFraisAddValid_Click(object sender, EventArgs e)
        {
            //Test if all inputs are not empty
            if (this.labelAddFicheFraisComboBox.SelectedIndex.Equals(-1) || this.FicheFraisNumericUpDown.Value.Equals(0))
            {
                MessageBox.Show("Veuillez rensigner tous les champs.");
            }
            else
            {
                //Get informations
                Functions.Item item = (Functions.Item)this.labelAddFicheFraisComboBox.SelectedItem;
                Int32 quantity = (Int32)this.FicheFraisNumericUpDown.Value;
                string date = DateTime.Now.ToString("MMyyyy");

                var getReq = from l in this.ctx.lignefraisforfait
                             where l.idVisiteur == this.user.id &&
                                   l.mois == date &&
                                   l.idFraisForfait == item.Value
                             select l;

                //Row exist
                if (getReq.Any())
                {
                    getReq.First().quantite = getReq.First().quantite + quantity;
                    this.ctx.SubmitChanges();
                    initFraisForfaits();
                }

                //Row not exist
                else
                {
                    var ligne = new lignefraisforfait { idVisiteur = this.user.id, mois = date, idFraisForfait = item.Value, quantite = quantity };
                    this.ctx.lignefraisforfait.InsertOnSubmit(ligne);
                    this.ctx.SubmitChanges();
                    initFraisForfaits();
                }                
            }            
        }

        /// <summary>
        /// Function for add Hors Frais
        /// </summary>
        /// <param name="sender"> Object </param>
        /// <param name="e"> Events </param>
        private void labelAddFicheHorsFraisButton_Click(object sender, EventArgs e)
        {
            //Test if all inputs are not empty
            if (this.labelAddFicheHorsFraisLibelle.Text.Equals("") || this.labelAddFicheHorsFraisNumericUpDown.Value.Equals(0))
            {
                MessageBox.Show("Le champ date (ou libellé ou montant) doit être renseigné");
            }
            else
            {
                //Get informations
                string libelle = this.labelAddFicheHorsFraisTextBox.Text;
                Int32 montant = (Int32)this.labelAddFicheHorsFraisNumericUpDown.Value;
                string dateNow = DateTime.Now.ToString("MMyyyy");
                DateTime date = this.labelAddFicheHorsFraisDateTimePicker.Value;

                //Date for compare (Date - 1 Year)
                DateTime dateCompare = Functions.getDateLessOneYear(DateTime.Now);
                
                //if date < dateCompare
                if (date.CompareTo(dateCompare) < 0)
                {
                    MessageBox.Show("La date d'engagement doit se situer dans l’année écoulée");
                }
                else
                {
                    var ligne = new lignefraishorsforfait { idVisiteur = this.user.id, mois = dateNow, libelle = libelle, date = date, montant = montant };
                    this.ctx.lignefraishorsforfait.InsertOnSubmit(ligne);
                    this.ctx.SubmitChanges();
                    initFraisHorsForfaits();
                }
            }
        }

        /// <summary>
        /// Function for add elements in comboBox select fraisforfaits
        /// </summary>
        private void initComboBox()
        {
            var elements = from f in this.ctx.fraisforfait
                           select f;
            foreach (var element in elements)
            {
                this.labelAddFicheFraisComboBox.Items.Add(new Functions.Item(element.libelle, element.id));
            }
            this.labelAddFicheFraisComboBox.DropDownStyle = ComboBoxStyle.DropDownList;
        }

        /// <summary>
        /// Function for init Fiches
        /// </summary>
        private void initFiche()
        {
            //Request BDD
            var ficheReq = from f in this.ctx.fichefrais
                        where f.idVisiteur == this.user.id &&
                              f.idEtat == "CR"
                        select f;

            //Fiche exist
            if (ficheReq.Any())
            {
                var fiche = ficheReq.First();
                
                //If fiche month is not current month
                if (!fiche.mois.Equals(DateTime.Now.ToString("MMyyyy")))
                {
                    fiche.idEtat = "CL";
                    this.ctx.SubmitChanges();
                    insertNewFiche();
                }
            }
            else//Fiche not exist
            {
                insertNewFiche();
            }
           
        }

        /// <summary>
        /// Function for insert a new fiche
        /// </summary>
        private void insertNewFiche()
        {
            Functions.insertNewFiche(this.ctx, user.id, DateTime.Now.ToString("MMyyyy"), true);
            getFiches();
        }

        /// <summary>
        /// Function for init add panel
        /// </summary>
        private void initAddPanel()
        {
            initFraisForfaits();
            initFraisHorsForfaits();            
        }

        /// <summary>
        /// Function for init ligne forfaits
        /// </summary>
        private void initFraisForfaits()
        {
            //Reset DataSource
            this.labelAddFicheFraisDataGridView.DataSource = null;
            //Get elements
            var lignesForfaits = Functions.getLignesForfaits(this.ctx, this.user, DateTime.Now.ToString("MMyyyy"));
            //Set DataSource
            this.labelAddFicheFraisDataGridView.DataSource = lignesForfaits;
        }

        /// <summary>
        /// Function for init ligne hors forfaits
        /// </summary>
        private void initFraisHorsForfaits()
        {
            //Reset DataSource
            this.labelAddFicheHorsFraisDataGridView.DataSource = null;
            //Get elements
            var lignesHorsForfaits = Functions.getLignesHorsForfaits(this.ctx, this.user, DateTime.Now.ToString("MMyyyy"));
            //Set DataSource
            this.labelAddFicheHorsFraisDataGridView.DataSource = lignesHorsForfaits;
        }

        /// <summary>
        /// Function for get fiche
        /// </summary>
        private void getFiches()
        {
            //Get all fiche with CL status for user
            List<fichefrais> fiche =
                (from f in this.ctx.fichefrais
                 where f.idVisiteur == this.user.id && f.idEtat == "CL"
                 select f).ToList();

            //Set elements in comboBox
            foreach (var element in fiche)
            {
                var libelle = Functions.getDateWithString(element.mois);
                this.comboBoxView.Items.Add(new Functions.Item(libelle, element.mois));
            }
            this.comboBoxView.DropDownStyle = ComboBoxStyle.DropDownList;
        }

        /// <summary>
        /// Function for get informations in ViewCombo Panel
        /// </summary>
        /// <param name="sender"> Object </param>
        /// <param name="e"> Events </param>
        private void buttonComboView_Click(object sender, EventArgs e)
        {
            //Test if input date has a value
            if (this.comboBoxView.SelectedIndex.Equals(-1))
            {
                MessageBox.Show("Veuillez sélectionner un mois.");
            }
            else
            {
                Functions.Item item = (Functions.Item)this.comboBoxView.SelectedItem;
                var fiche = 
                    (from f in this.ctx.fichefrais
                        where f.idVisiteur == this.user.id && f.mois == item.Value && f.idEtat == "CL"
                        select new { mois = f.mois, etat = f.etat.libelle }).First();
                this.textBoxCombo.Text = fiche.etat;
                this.textBoxComboDate.Text = Functions.getDateWithString(fiche.mois);

                //Reset DataSource
                this.dataGridViewComboForfaitise.DataSource = null;

                //Update in BDD
                var lignesForfaits = Functions.getLignesForfaits(this.ctx, this.user, item.Value);

                //Set DataSource
                this.dataGridViewComboForfaitise.DataSource = lignesForfaits;
                this.dataGridViewComboForfaitise.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.AllCells;

                //Reset DataSource
                this.dataGridViewComboNonForfaitise.DataSource = null;

                //Update in BDD
                var lignesHorsForfaits = Functions.getLignesHorsForfaits(this.ctx, this.user, item.Value);

                //Set DataSource
                this.dataGridViewComboNonForfaitise.DataSource = lignesHorsForfaits;
                this.dataGridViewComboNonForfaitise.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.AllCells;

                this.panelComboView.Visible = true;
            }
        }
    }
}
