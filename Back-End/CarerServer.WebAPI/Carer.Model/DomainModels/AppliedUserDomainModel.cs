using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Carer.Model.DomainModels
{
    public class AppliedUserDomainModel
    {
        private Guid userId;
        private Guid adId;
        private string status = "pending";
        private string userName;
        private string pictureURL;
        private int rating;
        private DateTime dateApplied = DateTime.UtcNow;

        public Guid UserId { get => userId; set => userId = value; }
        public Guid AdId { get => adId; set => adId = value; }
        public DateTime DateApplied { get => dateApplied; set => dateApplied = value; }
        public string UserName { get => userName; set => userName = value; }
        public int Rating { get => rating; set => rating = value; }
        public string Status { get => status; set => status = value; }
        public string PictureURL { get => pictureURL; set => pictureURL = value; }
    }
}
