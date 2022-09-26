using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Carer.Model.DomainModels
{
    public class CarerReviewDomainModel
    {
        private Guid id;
        private Guid carerId;
        private int overallRating;
        private int petHandlingRating;
        private int courtesyRating;
        private bool isPoster;

        public Guid Id { get => id; set => id = value; }
        public Guid CarerId { get => carerId; set => carerId = value; }
        public int OverallRating { get => overallRating; set => overallRating = value; }
        public int PetHandlingRating { get => petHandlingRating; set => petHandlingRating = value; }
        public int CourtesyRating { get => courtesyRating; set => courtesyRating = value; }
        public bool IsPoster { get => isPoster; set => isPoster = value; }
    }
}
