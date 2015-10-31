using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.Linq;
using System.Drawing;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace GSB
{
    public partial class Comptable : Form
    {
        /// <summary>
        /// Attributs
        /// </summary>
        private visiteur currentUser;
        private dbDataContext ctx = new dbDataContext();

        private Functions.Item viewUser;
        private string viewDate;

        private string key = "REFUSE : ";
        private string format = "{0:dd/MM/yyyy}";

        private fichefrais ficheValider = null;
        private fichefrais ficheSuivi = null;

        /// <summary>
        /// Constructor
        /// </summary>
        public Comptable()
        {
            InitializeComponent();
        }

        /// <summary>
        /// Constructor with an user
        /// </summary>
        /// <param name="currentUser"> visiteur </param>
        public Comptable(visiteur currentUser)
        {
            InitializeComponent();
            this.currentUser = currentUser;
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
        /// Function for init form
        /// </summary>
        /// <param name="sender"> Object </param>
        /// <param name="e"> Events </param>
        private void Comptable_Load(object sender, EventArgs e)
        {
            this.labelLogout.Text = "Vous êtes connecté en tant que " + currentUser.nom.Replace(" ", string.Empty) + " " + currentUser.prenom.Replace(" ", string.Empty) + ", comptable";

            checkFiche();

            initComboBox();

            this.dateTimePickerSelectDate.Format = DateTimePickerFormat.Custom;
            this.dateTimePickerSelectDate.CustomFormat = "MM/yyyy";
            this.dateTimePickerSelectDateSuivi.Format = DateTimePickerFormat.Custom;
            this.dateTimePickerSelectDateSuivi.CustomFormat = "MM/yyyy";
        }

        /// <summary>
        /// Function for paint a line
        /// </summary>
        /// <param name="e"> Events </param>
        protected override void OnPaint(PaintEventArgs e)
        {
            base.OnPaint(e);
            Graphics g;

            g = e.Graphics;

            Pen myPen = new Pen(Color.Black, 1);
            g.DrawLine(myPen, 0, this.buttonSuivi.Location.Y + 40, 10000, this.buttonSuivi.Location.Y + 40);
        }

        /// <summary>
        /// Function to start on click in buttonView button
        /// </summary>
        /// <param name="sender"> Object </param>
        /// <param name="e"> Events </param>
        private void buttonView_Click(object sender, EventArgs e)
        {
            this.panelSuivi.Visible = false;
            this.panelView.Visible = true;
        }

        /// <summary>
        /// Function to start on click in buttonSuivi button
        /// </summary>
        /// <param name="sender"> Object </param>
        /// <param name="e"> Events </param>
        private void buttonSuivi_Click(object sender, EventArgs e)
        {
            this.panelSuivi.Visible = true;
            this.panelView.Visible = false;
        }

        /// <summary>
        /// Function for help to check fiche
        /// </summary>
        private void checkFiche()
        {
            var fiches = from f in this.ctx.fichefrais
                        where f.idEtat == "CR"
                        select f;

            if (fiches.Any())
            {
                foreach (var fiche in fiches)
                {
                    var mois = Convert.ToDateTime(Functions.getDateWithString(fiche.mois));
                    var moisCompare = Convert.ToDateTime(DateTime.Now.ToString("MM/yyyy"));
                    if (mois < moisCompare)
                    {
                        fiche.idEtat = "CL";
                    }
                }
                this.ctx.SubmitChanges();
            }
        }

        /// <summary>
        /// Function for add elements in comboBox
        /// </summary>
        private void initComboBox()
        {
            var elements = from u in this.ctx.visiteur
                           select u;

            foreach (var element in elements)
            {
                this.comboBoxSelectUser.Items.Add(new Functions.Item(element.login, element.id));
                this.comboBoxSelectUserSuivi.Items.Add(new Functions.Item(element.login, element.id));
            }
            this.comboBoxSelectUser.DropDownStyle = ComboBoxStyle.DropDownList;
            this.comboBoxSelectUserSuivi.DropDownStyle = ComboBoxStyle.DropDownList;
        }

        /// <summary>
        /// Function to start on click in buttonValidateSelect button
        /// </summary>
        /// <param name="sender"> Object </param>
        /// <param name="e"> Events </param>
        private void buttonValidateSelect_Click(object sender, EventArgs e)
        {
            if (this.comboBoxSelectUser.SelectedIndex == -1)
            {
                MessageBox.Show("Veuillez sélectionner un utilisateur");
            }
            else
            {
                viewUser = (Functions.Item)this.comboBoxSelectUser.SelectedItem;
                viewDate = this.dateTimePickerSelectDate.Value.ToString("MMyyyy");

                initDatas();
            }
        }

        /// <summary>
        /// Function for init datas in inputs
        /// </summary>
        private void initDatas()
        {
            var fiche = from f in this.ctx.fichefrais
                        where f.idVisiteur == viewUser.Value && f.mois == viewDate && f.idEtat == "CL"
                        select f;

            if (!fiche.Any())
            {
                MessageBox.Show("Pas de fiche de frais pour ce visiteur ce mois-ci");
            }
            else
            {
                this.ficheValider = fiche.First();

                setInfosValidate(this.ficheValider.dateModif, this.ficheValider.etat.libelle);
                getLignesForfaits();
                getLignesHorsForfaits();

                this.labelDate.Visible = true;
                this.labelEtat.Visible = true;
                this.textBoxEtat.Visible = true;
                this.textBoxDate.Visible = true;
                this.labelForfaitiseViewComptable.Visible = true;
                this.labelNonForfaitiseViewComptable.Visible = true;
                this.dataGridViewComptableForfaitise.Visible = true;
                this.dataGridViewComptableNonForfaitise.Visible = true;
                this.buttonValidateComptableView.Visible = true;
                this.buttonResetComptableView.Visible = true;

                dataGridViewComptableForfaitise.CellMouseDown += dataGridViewComptableForfaitise_CellMouseDown;
            }
        }

        /// <summary>
        /// Function for set datas in textBoxDate
        /// </summary>
        /// <param name="date"> DateTime </param>
        /// <param name="etat"> String </param>
        private void setInfosValidate(DateTime? date, string etat)
        {
            this.textBoxDate.Text = string.Format(format, (DateTime)date);
            this.textBoxEtat.Text = etat;
        }

        /// <summary>
        /// Function for get Lignes Forfaits
        /// </summary>
        private void getLignesForfaits()
        {
            this.dataGridViewComptableForfaitise.DataSource = null;
            var lignesForfaits = Functions.getLignesForfaits(this.ctx, viewUser, viewDate);
            this.dataGridViewComptableForfaitise.DataSource = lignesForfaits;
            this.dataGridViewComptableForfaitise.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.AllCells;
        }

        /// <summary>
        /// Function for get Lignes Hors Forfaits
        /// </summary>
        List<lignefraishorsforfait> listLignesHorsForfaits;
        private void getLignesHorsForfaits()
        {
            this.dataGridViewComptableNonForfaitise.DataSource = null;
            //this.ctx = new dbDataContext();
            //this.ctx.Refresh(RefreshMode.OverwriteCurrentValues, someObject);

            var lignesHorsForfaits = Functions.getLignesHorsForfaits(this.ctx, viewUser, viewDate);

            listLignesHorsForfaits = (from l in ctx.lignefraishorsforfait
                                      where l.idVisiteur == viewUser.Value && l.mois == viewDate
                                      select l).OfType<lignefraishorsforfait>().ToList();

            this.dataGridViewComptableNonForfaitise.DataSource = lignesHorsForfaits;
            this.dataGridViewComptableNonForfaitise.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.AllCells;
        }

        /// <summary>
        /// Function to start on click in cell on dataGridViewComptableForfaitise
        /// </summary>
        /// <param name="sender"> Object </param>
        /// <param name="e"> Events </param>
        private void dataGridViewComptableForfaitise_CellMouseDown(object sender, DataGridViewCellMouseEventArgs e)
        {
            // Ignore if a column or row header is clicked AND if first column
            if (e.RowIndex != -1 && e.ColumnIndex != -1 && e.ColumnIndex != 0)
            {
                if (e.Button == MouseButtons.Right)
                {
                    DataGridViewCell clickedCell = (sender as DataGridView).Rows[e.RowIndex].Cells[e.ColumnIndex];

                    // Here you can do whatever you want with the cell
                    this.dataGridViewComptableForfaitise.CurrentCell = clickedCell;  // Select the clicked cell, for instance

                    // Get mouse position relative to the vehicles grid
                    var relativeMousePosition = dataGridViewComptableForfaitise.PointToClient(Cursor.Position);

                    // Show the context menu
                    ContextMenuStrip mnu = new ContextMenuStrip();
                    ToolStripMenuItem mnuEdit = new ToolStripMenuItem("Editer");
                    mnuEdit.Click += mnuEdit_Click;
                    mnu.Items.AddRange(new ToolStripItem[] { mnuEdit });
                    mnu.Show(dataGridViewComptableForfaitise, relativeMousePosition);
                }
            }
        }

        /// <summary>
        /// Function for edit choice
        /// </summary>
        /// <param name="sender"> Object </param>
        /// <param name="e"> Events </param>
        private void mnuEdit_Click(object sender, EventArgs e)
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

                    string nameChange = dgv.Rows[dgv.CurrentCell.RowIndex].Cells[dgv.CurrentCell.ColumnIndex - 1].Value.ToString();
                    Console.WriteLine(nameChange);
                    int valueChange = (int)dgv.CurrentCell.Value;

                    this.labelModifyForfait.Text = this.labelModifyForfait.Text.Replace("{name}", nameChange.Replace(" ", string.Empty));
                    this.numericUpDownModifyForfait.Value = valueChange;

                    this.numericUpDownModifyForfait.Visible = true;
                    this.labelModifyForfait.Visible = true;
                    this.buttonModifyForfait.Visible = true;
                }
            }
        }

        /// <summary>
        /// Function to start on click in buttonModifyForfait button
        /// </summary>
        /// <param name="sender"> Object </param>
        /// <param name="e"> Events </param>
        private void buttonModifyForfait_Click(object sender, EventArgs e)
        {
            int saveValue = (int)numericUpDownModifyForfait.Value;
            string nameSave = this.dataGridViewComptableForfaitise.Rows[this.dataGridViewComptableForfaitise.CurrentCell.RowIndex].Cells[this.dataGridViewComptableForfaitise.CurrentCell.ColumnIndex - 1].Value.ToString();

            var ligne = from f in this.ctx.lignefraisforfait
                        where f.idVisiteur == viewUser.Value && f.mois == viewDate && f.fraisforfait.libelle == nameSave
                        select f;
            ligne.First().quantite = saveValue;
            this.ctx.SubmitChanges();

            getLignesForfaits();

            this.numericUpDownModifyForfait.Visible = false;
            this.labelModifyForfait.Visible = false;
            this.buttonModifyForfait.Visible = false;
            this.labelValidateComptableForfaitise.Visible = true;
        }

        /// <summary>
        /// Function to start on click in cell on dataGridViewComptableNonForfaitise
        /// </summary>
        /// <param name="sender"> Object </param>
        /// <param name="e"> Events </param>
        private void dataGridViewComptableNonForfaitise_CellMouseDown(object sender, DataGridViewCellMouseEventArgs e)
        {
            // Ignore if a column or row header is clicked
            if (e.RowIndex != -1 && e.ColumnIndex != -1)
            {
                if (e.Button == MouseButtons.Right)
                {
                    DataGridViewCell clickedCell = (sender as DataGridView).Rows[e.RowIndex].Cells[e.ColumnIndex];

                    // Here you can do whatever you want with the cell
                    this.dataGridViewComptableNonForfaitise.CurrentCell = clickedCell;  // Select the clicked cell, for instance

                    // Get mouse position relative to the vehicles grid
                    var relativeMousePosition = dataGridViewComptableNonForfaitise.PointToClient(Cursor.Position);

                    // Show the context menu
                    if (!this.dataGridViewComptableNonForfaitise.Rows[this.dataGridViewComptableNonForfaitise.CurrentCell.RowIndex].Cells[0].Value.ToString().Contains(this.key))
                    {
                        ContextMenuStrip mnu = new ContextMenuStrip();
                        ToolStripMenuItem mnuReport = new ToolStripMenuItem("Reporter");
                        mnuReport.Click += mnuReport_Click;
                        ToolStripMenuItem mnuSuppr = new ToolStripMenuItem("Supprimer");
                        mnuSuppr.Click += mnuSuppr_Click;
                        mnu.Items.AddRange(new ToolStripItem[] { mnuReport, mnuSuppr });
                        mnu.Show(dataGridViewComptableNonForfaitise, relativeMousePosition);
                    }
                }
            }
        }

        /// <summary>
        /// Function for report choice
        /// </summary>
        /// <param name="sender"> Object </param>
        /// <param name="e"> Events </param>
        private void mnuReport_Click(object sender, EventArgs e)
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

                    insertNewLigne(listLignesHorsForfaits.ElementAt(dgv.CurrentCell.RowIndex));

                    listLignesHorsForfaits.RemoveAt(dgv.CurrentCell.RowIndex);
                    dgv.Rows.Remove(dgv.Rows[dgv.CurrentCell.RowIndex]);
                }
            }
        }

        /// <summary>
        /// Function for set new line in BDD
        /// </summary>
        /// <param name="ligne"> lignefraishorsforfait </param>
        private void insertNewLigne(lignefraishorsforfait ligne)
        {
            string mois = ((DateTime)ligne.date).AddMonths(1).ToString("MMyyyy");

            var ficheReq = from f in this.ctx.fichefrais
                           where f.idVisiteur == this.viewUser.Value &&
                                 f.idEtat == "CR" && f.mois == mois
                           select f;
            if (!ficheReq.Any())
            {
                Functions.insertNewFiche(this.ctx, viewUser.Value, mois, false);
            }

            //insert
            var newLigne = new lignefraishorsforfait
            {
                idVisiteur = ligne.idVisiteur,
                libelle = ligne.libelle,
                montant = ligne.montant,
                mois = mois,
                date = DateTime.Now
            };
            this.ctx.lignefraishorsforfait.InsertOnSubmit(newLigne);

            var listShow = (from l in ctx.lignefraishorsforfait
                            where l.idVisiteur == viewUser.Value && l.mois == viewDate
                            select l).ToList();

        }

        /// <summary>
        /// Function for delete choice
        /// </summary>
        /// <param name="sender"> Object </param>
        /// <param name="e"> Events </param>
        private void mnuSuppr_Click(object sender, EventArgs e)
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

                    string textChange = dgv.Rows[dgv.CurrentCell.RowIndex].Cells[0].Value.ToString();
                    textChange = "REFUSE : " + textChange;
                    if (textChange.Count() > 100)
                    {
                        textChange = textChange.Substring(0, 97) + "...";
                    }

                    var listShow = (from l in ctx.lignefraishorsforfait
                               where l.idVisiteur == viewUser.Value && l.mois == viewDate
                               select new Lignes { Libelle = l.libelle, Date = (DateTime)l.date, Montant = (int)l.montant }).ToList();

                    for (var i = 0; i < listShow.Count(); i++)
                    {
                        if (i == dgv.Rows[dgv.CurrentCell.RowIndex].Index)
                        {
                            listShow.ElementAt(i).Libelle = textChange;
                            listLignesHorsForfaits.ElementAt(i).libelle = textChange;
                        }
                    }

                    this.dataGridViewComptableNonForfaitise.DataSource = null;
                    this.dataGridViewComptableNonForfaitise.DataSource = listShow;
                }
            }
        }

        /// <summary>
        /// Function to start on click in buttonResetComptableView button
        /// </summary>
        /// <param name="sender"> Object </param>
        /// <param name="e"> Events </param>
        private void buttonResetComptableView_Click(object sender, EventArgs e)
        {
            this.ctx = new dbDataContext();
            initDatas();
        }

        /// <summary>
        /// Function to start on click in buttonValidateComptableView button
        /// </summary>
        /// <param name="sender"> Object </param>
        /// <param name="e"> Events </param>
        private void buttonValidateComptableView_Click(object sender, EventArgs e)
        {
            this.ficheValider.etat = this.ctx.etat.FirstOrDefault(E => E.id == "VA");
            this.ficheValider.dateModif = DateTime.Now;
            this.ctx.SubmitChanges();
            getLignesHorsForfaits();

            setInfosValidate(this.ficheValider.dateModif, this.ficheValider.etat.libelle);

            this.buttonResetComptableView.Visible = false;
            this.buttonValidateComptableView.Visible = false;
            this.dataGridViewComptableForfaitise.CellMouseDown -= dataGridViewComptableForfaitise_CellMouseDown;
            this.dataGridViewComptableNonForfaitise.CellMouseDown -= dataGridViewComptableNonForfaitise_CellMouseDown;
        }

        /// <summary>
        /// Function to start on click in buttonValiderSuivi button
        /// </summary>
        /// <param name="sender"> Object </param>
        /// <param name="e"> Events </param>
        private void buttonValiderSuivi_Click(object sender, EventArgs e)
        {
            if (this.comboBoxSelectUserSuivi.SelectedIndex == -1)
            {
                MessageBox.Show("Veuillez sélectionner un utilisateur");
            }
            else
            {
                viewUser = (Functions.Item)this.comboBoxSelectUserSuivi.SelectedItem;
                viewDate = this.dateTimePickerSelectDateSuivi.Value.ToString("MMyyyy");

                var listFiche = from f in this.ctx.fichefrais
                                join E in this.ctx.etat
                                on f.idEtat equals E.id
                            where f.idVisiteur == viewUser.Value && f.mois == viewDate && (f.idEtat == "VA" || f.idEtat == "MP" || f.idEtat == "RB")
                            select f;

                if (!listFiche.Any())
                {
                    MessageBox.Show("Pas de fiche de frais pour ce visiteur ce mois");
                }
                else
                {
                    this.ficheSuivi = listFiche.First();
                    this.textBoxEtatSuivi.Text = this.ficheSuivi.etat.libelle;
                    this.textBoxDateSuivi.Text = string.Format(format, this.ficheSuivi.dateModif);

                    //Init datagridview's
                    this.dataGridViewFraisSuivi.DataSource = null;
                    var lignesForfaits = Functions.getLignesForfaits(this.ctx, viewUser, viewDate);
                    this.dataGridViewFraisSuivi.DataSource = lignesForfaits;
                    this.dataGridViewFraisSuivi.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.AllCells;

                    this.dataGridViewNonFraisSuivi.DataSource = null;
                    var lignesHorsForfaits = Functions.getLignesHorsForfaits(this.ctx, viewUser, viewDate);
                    this.dataGridViewNonFraisSuivi.DataSource = lignesHorsForfaits;
                    this.dataGridViewNonFraisSuivi.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.AllCells;

                    this.labelComboDateSuivi.Visible = true;
                    this.labelComboEtatSuivi.Visible = true;
                    this.textBoxEtatSuivi.Visible = true;
                    this.textBoxDateSuivi.Visible = true;

                    this.dataGridViewFraisSuivi.Visible = true;
                    this.dataGridViewNonFraisSuivi.Visible = true;

                    if (this.ficheSuivi.idEtat == "VA")
                    {
                        this.buttonSuiviPaiement.Visible = true;
                        this.buttonSuiviRembourse.Visible = true;
                    }
                    else if (this.ficheSuivi.idEtat == "MP")
                    {
                        this.buttonSuiviRembourse.Visible = true;
                    }
                }
            }
        }

        /// <summary>
        /// Function to set datas in inputs
        /// </summary>
        /// <param name="date"> DateTime </param>
        /// <param name="etat"> String </param>
        private void setInfosSuivi(DateTime? date, string etat)
        {
            this.textBoxDateSuivi.Text = string.Format(format, (DateTime)date);
            this.textBoxEtatSuivi.Text = etat;
        }

        /// <summary>
        /// Function to start on click in buttonSuiviPaiement button
        /// </summary>
        /// <param name="sender"> Object </param>
        /// <param name="e"> Events </param>
        private void buttonSuiviPaiement_Click(object sender, EventArgs e)
        {
            this.ficheSuivi.etat = this.ctx.etat.FirstOrDefault(E => E.id == "MP");
            this.ficheSuivi.dateModif = DateTime.Now;
            this.ctx.SubmitChanges();

            setInfosSuivi(this.ficheSuivi.dateModif, this.ficheSuivi.etat.libelle);

            this.buttonSuiviPaiement.Visible = false;
        }

        /// <summary>
        /// Function to start on click in buttonSuiviRembourse button
        /// </summary>
        /// <param name="sender"> Object </param>
        /// <param name="e"> Events </param>
        private void buttonSuiviRembourse_Click(object sender, EventArgs e)
        {
            this.ficheSuivi.etat = this.ctx.etat.FirstOrDefault(E => E.id == "RB");
            this.ficheSuivi.dateModif = DateTime.Now;
            this.ctx.SubmitChanges();

            setInfosSuivi(this.ficheSuivi.dateModif, this.ficheSuivi.etat.libelle);

            this.buttonSuiviPaiement.Visible = false;
            this.buttonSuiviRembourse.Visible = false;
        }

    }

    /// <summary>
    /// Class Lignes for structure data in dataGridView
    /// </summary>
    class Lignes
    {
        private string libelle;
        public string Libelle
        {
            get { return libelle; }
            set { libelle = value; }
        }

        private DateTime date;
        public DateTime Date
        {
            get { return date; }
            set { date = value; }
        }

        private int montant;
        public int Montant
        {
            get { return montant; }
            set { montant = value; }
        }
    }
}